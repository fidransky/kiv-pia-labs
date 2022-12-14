package cz.zcu.kiv.pia.labs;

import graphql.scalars.ExtendedScalars;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.graphql.data.method.annotation.support.AnnotatedControllerConfigurer;
import org.springframework.graphql.execution.*;
import org.springframework.graphql.server.WebGraphQlHandler;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.webflux.GraphQlHttpHandler;
import org.springframework.graphql.server.webflux.GraphQlWebSocketHandler;
import org.springframework.graphql.server.webflux.GraphiQlHandler;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketUpgradeHandlerPredicate;

import java.time.Duration;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class GraphQLConfig {
    @Value("classpath:graphql/api.graphqls")
    private Resource schema;

    @Bean
    public AnnotatedControllerConfigurer annotatedControllerConfigurer() {
        return new AnnotatedControllerConfigurer();
    }

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.DateTime);
    }

    @Bean
    public GraphQlSource graphQlSource(AnnotatedControllerConfigurer annotatedControllerConfigurer, RuntimeWiringConfigurer runtimeWiringConfigurer) {
        return GraphQlSource.schemaResourceBuilder()
                .schemaResources(schema)
                .configureRuntimeWiring(annotatedControllerConfigurer)
                .configureRuntimeWiring(runtimeWiringConfigurer)
                .build();
    }

    @Bean
    public BatchLoaderRegistry batchLoaderRegistry() {
        return new DefaultBatchLoaderRegistry();
    }

    @Bean
    public ExecutionGraphQlService executionGraphQlService(GraphQlSource graphQlSource, BatchLoaderRegistry batchLoaderRegistry) {
        DefaultExecutionGraphQlService service = new DefaultExecutionGraphQlService(graphQlSource);
        service.addDataLoaderRegistrar(batchLoaderRegistry);
        return service;
    }

    @Bean
    public GraphQlHttpHandler graphQlHttpHandler(ExecutionGraphQlService service) {
        return new GraphQlHttpHandler(WebGraphQlHandler.builder(service).build());
    }

    @Bean
    public GraphiQlHandler graphiQlHandler() {
        return new GraphiQlHandler("/graphql", "/graphql");
    }

    @Bean
    public RouterFunction<ServerResponse> routes(GraphQlHttpHandler graphQlHttpHandler, GraphiQlHandler graphiQlHandler) {
        return RouterFunctions
                .route(POST("/graphql").and(accept(MediaType.APPLICATION_JSON)), graphQlHttpHandler::handleRequest)
                .andRoute(GET("/graphiql").and(accept(MediaType.TEXT_HTML)), graphiQlHandler::handleRequest);
    }

    @Bean
    public WebGraphQlHandler webGraphQlHandler(ExecutionGraphQlService service, ObjectProvider<WebGraphQlInterceptor> interceptorsProvider) {
        return WebGraphQlHandler.builder(service).interceptors(interceptorsProvider.orderedStream().collect(Collectors.toList())).build();
    }

    @Bean
    public GraphQlWebSocketHandler graphQlWebSocketHandler(WebGraphQlHandler webGraphQlHandler, ServerCodecConfigurer configurer) {
        return new GraphQlWebSocketHandler(webGraphQlHandler, configurer, Duration.ofSeconds(60));
    }

    @Bean
    public HandlerMapping graphQlWebSocketEndpoint(GraphQlWebSocketHandler graphQlWebSocketHandler) {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setHandlerPredicate(new WebSocketUpgradeHandlerPredicate());
        mapping.setUrlMap(Collections.singletonMap("/graphql", graphQlWebSocketHandler));
        mapping.setOrder(-2);
        return mapping;
    }
}

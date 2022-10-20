package cz.zcu.kiv.pia.labs;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.thymeleaf.spring5.ISpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.SpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Map;

@Configuration
public class ThymeleafConfig implements WebFluxConfigurer {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private MessageSource messageSource;

    @Bean
    public ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        // Load HTML templates from src/main/resources/templates/ directory
        templateResolver.setPrefix("classpath:/templates/");
        // Append .html suffix to view names returned from controller methods before template lookup
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public ISpringWebFluxTemplateEngine templateEngine() {
        SpringWebFluxTemplateEngine templateEngine = new SpringWebFluxTemplateEngine();
        // WARNING: Use SpringTemplateEngine instead of SpringWebFluxTemplateEngine with Spring MVC:
        //SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setTemplateEngineMessageSource(messageSource);
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.addDialect(new LayoutDialect()); // add the ability to decorate templates
        return templateEngine;
    }

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafReactiveViewResolver viewResolver = new ThymeleafReactiveViewResolver();
        // WARNING: Use ThymeleafViewResolver instead of ThymeleafReactiveViewResolver with Spring MVC:
        //ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        // Set common "appName" variable usable in all templates
        viewResolver.setStaticVariables(Map.of("appName", "KIV/PIA Labs"));
        return viewResolver;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(viewResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map static resources loaded under root path to src/main/resources/static/ directory
        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}

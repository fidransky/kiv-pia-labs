package cz.zcu.kiv.pia.labs;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.zcu.kiv.pia.labs.facade.ProjectToolsFacade;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

@Configuration
public class MCPConfiguration {
    @Bean
    public ToolCallbackProvider toolCallbackProvider(ProjectToolsFacade projectTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(projectTools)
                .build();
    }

    @Bean
    public List<McpServerFeatures.SyncResourceSpecification> systemResources() {
        var systemInfoResource = McpSchema.Resource.builder()
                .uri("/system-info")
                .name("system-info")
                .title("System info")
                .mimeType(MediaType.APPLICATION_JSON_VALUE)
                .build();

        var resourceSpecification = new McpServerFeatures.SyncResourceSpecification(systemInfoResource, (exchange, request) -> {
            try {
                var systemInfo = Map.of(
                        "java-version", System.getProperty("java.version")
                );

                // write system info as JSON
                String jsonContent = new ObjectMapper().writeValueAsString(systemInfo);

                return new McpSchema.ReadResourceResult(List.of(
                                new McpSchema.TextResourceContents(request.uri(), MediaType.APPLICATION_JSON_VALUE, jsonContent)
                ));

            } catch (Exception e) {
                throw new RuntimeException("Failed to generate system info", e);
            }
        });

        return List.of(resourceSpecification);
    }
}

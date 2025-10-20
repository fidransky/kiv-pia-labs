package cz.zcu.kiv.pia.labs.config;

import cz.zcu.kiv.pia.labs.mcp.PiaLabsMcpTools;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for MCP (Model Context Protocol) server using WebMVC.
 */
@Configuration
public class McpConfiguration {

    @Bean
    public ToolCallbackProvider toolCallbackProvider(PiaLabsMcpTools tools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(tools)
                .build();
    }
}

package cz.zcu.kiv.pia.labs;

import cz.zcu.kiv.pia.labs.facade.ProjectToolsFacade;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MCPConfiguration {
    @Bean
    public ToolCallbackProvider toolCallbackProvider(ProjectToolsFacade projectTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(projectTools)
                .build();
    }
}

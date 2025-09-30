package cz.zcu.kiv.pia.labs.mcp;

import cz.zcu.kiv.pia.labs.domain.Damage;
import cz.zcu.kiv.pia.labs.domain.Location;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.domain.UserRole;
import cz.zcu.kiv.pia.labs.service.DamageService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * MCP Tools for Insurance system.
 * These tools expose insurance damage reporting functionality to AI assistants.
 */
@Component
public class PiaLabsMcpTools {

    private final DamageService damageService;

    @Autowired
    public PiaLabsMcpTools(DamageService damageService) {
        this.damageService = damageService;
    }

    /**
     * Sets up a generic AI Assistant authentication context for MCP tool calls
     */
    private void setupAiAssistantAuth() {
        // Create a generic "AI Assistant" user for MCP operations with INSURED role
        User aiAssistant = new User(
            UUID.fromString("00000000-0000-0000-0000-000000000001"), // Fixed UUID for AI assistant
            "AI Assistant", 
            "ai-assistant@mcp.internal", 
            UserRole.INSURED // Set proper user role
        );

        // Create authentication with ROLE_INSURED to access damage service methods
        Authentication auth = new UsernamePasswordAuthenticationToken(
            aiAssistant,
            null,
            List.of(new SimpleGrantedAuthority("ROLE_INSURED"))
        );

        // Set in security context
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Tool(description = "Report a new damage incident for insurance claim")
    public Map<String, Object> reportDamage(
            @ToolParam(description = "Email address of the impaired person") String impairedEmail,
            @ToolParam(description = "When the damage occurred (ISO format: 2024-01-15T10:30:00)") String timestamp,
            @ToolParam(description = "Longitude coordinate of damage location") double longitude,
            @ToolParam(description = "Latitude coordinate of damage location") double latitude,
            @ToolParam(description = "Detailed description of the damage") String description) {

        // Set up AI Assistant authentication for this MCP call
        setupAiAssistantAuth();

        if (impairedEmail == null || impairedEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Impaired person's email is required");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Damage description is required");
        }

        try {
            // Create impaired user (simplified - in real app you'd look up existing user)
            User impaired = new User(UUID.randomUUID(), "Impaired Person", impairedEmail, null);

            // Parse timestamp
            Instant damageTimestamp = LocalDateTime.parse(timestamp).toInstant(ZoneOffset.UTC);

            // Create location
            Location location = new Location(BigDecimal.valueOf(longitude), BigDecimal.valueOf(latitude));

            // Report the damage
            Damage damage = damageService.create(impaired, damageTimestamp, location, description);

            return Map.of(
                "success", true,
                "damageId", damage.getId().toString(),
                "status", "reported",
                "message", "Damage has been successfully reported",
                "details", Map.of(
                    "impaired", impairedEmail,
                    "timestamp", timestamp,
                    "location", Map.of("longitude", longitude, "latitude", latitude),
                    "description", description
                )
            );

        } catch (Exception e) {
            return Map.of(
                "success", false,
                "error", e.getMessage(),
                "message", "Failed to report damage"
            );
        }
    }

    @Tool(description = "Get all damages reported by the current user")
    public Map<String, Object> getMyReportedDamages() {
        // Set up AI Assistant authentication for this MCP call
        setupAiAssistantAuth();

        try {
            Collection<Damage> damages = damageService.retrieveReportedDamage();

            List<Map<String, Object>> damageList = damages.stream()
                .map(damage -> Map.of(
                    "id", damage.getId().toString(),
                    "impaired", damage.getImpaired().getName(),
                    "timestamp", damage.getTimestamp().toString(),
                    "location", Map.of(
                        "longitude", damage.getLocation().getLongitude(),
                        "latitude", damage.getLocation().getLatitude()
                    ),
                    "description", damage.getDescription(),
                    "state", damage.getState().toString()
                ))
                .collect(Collectors.toList());

            return Map.of(
                "success", true,
                "totalDamages", damages.size(),
                "damages", damageList,
                "retrievedAt", LocalDateTime.now().toString()
            );

        } catch (Exception e) {
            return Map.of(
                "success", false,
                "error", e.getMessage(),
                "message", "Failed to retrieve reported damages"
            );
        }
    }

    @Tool(description = "Get insurance reporting guidelines and help")
    public Map<String, Object> getReportingGuidelines() {
        return Map.of(
            "guidelines", List.of(
                "Report damage as soon as possible after the incident",
                "Provide accurate location coordinates",
                "Include detailed description of what happened",
                "Take photos if possible (can be uploaded later)",
                "Keep any receipts or documentation related to the damage"
            ),
            "requiredInformation", List.of(
                "Date and time of incident",
                "Exact location (GPS coordinates preferred)",
                "Description of damage",
                "Contact information of impaired party"
            ),
            "supportContact", Map.of(
                "phone", "+420-555-123-456",
                "email", "support@insurance.example.com",
                "hours", "24/7 emergency hotline available"
            ),
            "generatedAt", LocalDateTime.now().toString()
        );
    }

    @Tool(description = "Get damage reporting statistics and summary")
    public Map<String, Object> getDamageStatistics() {
        // Set up AI Assistant authentication for this MCP call
        setupAiAssistantAuth();

        try {
            Collection<Damage> allDamages = damageService.retrieveReportedDamage();

            return Map.of(
                "totalReported", allDamages.size(),
                "reportedToday", allDamages.stream()
                    .filter(damage -> damage.getTimestamp().isAfter(
                        LocalDateTime.now().minusDays(1).toInstant(ZoneOffset.UTC)))
                    .count(),
                "commonDamageTypes", List.of("Vehicle damage", "Property damage", "Personal injury"),
                "averageProcessingTime", "3-5 business days",
                "statusDistribution", Map.of(
                    "new", "45%",
                    "processing", "35%",
                    "resolved", "20%"
                ),
                "generatedAt", LocalDateTime.now().toString()
            );

        } catch (Exception e) {
            return Map.of(
                "success", false,
                "error", e.getMessage(),
                "message", "Failed to retrieve damage statistics"
            );
        }
    }
}

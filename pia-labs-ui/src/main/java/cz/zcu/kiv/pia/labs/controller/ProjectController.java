package cz.zcu.kiv.pia.labs.controller;

import cz.zcu.kiv.pia.labs.controller.vo.ProjectVO;
import cz.zcu.kiv.pia.labs.controller.vo.UserVO;
import cz.zcu.kiv.pia.labs.domain.Project;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Locale;
import java.util.UUID;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/projects/{id}")
    public String projectDetails(@PathVariable("id") UUID projectId, Model model, RedirectAttributes redirectAttributes) {
        try {
            Project project = projectService.getProjectById(projectId);
            ProjectVO projectVO = mapToProjectVO(project);

            model.addAttribute("project", projectVO);

            return "project-details";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error loading project: " + e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/projects/{id}/complete")
    public String markProjectComplete(@PathVariable("id") UUID projectId, RedirectAttributes redirectAttributes) {
        try {
            Project project = projectService.getProjectById(projectId);

            // Check if project can be completed
            if (project.isCompleted()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Project is already completed");
                return "redirect:/projects/" + projectId;
            }

            // Mock translator assignment if not already assigned
            if (project.getTranslator() == null) {
                // Create mock translator user
                User mockTranslator = createMockTranslator();
                project.assignTranslator(mockTranslator);
            }

            // Create mock translated file content
            byte[] mockTranslatedFile = createMockTranslatedFile(project.getTargetLanguage());

            // Complete the project
            projectService.completeProject(projectId, mockTranslatedFile);

            redirectAttributes.addFlashAttribute("successMessage", 
                "Project marked as complete with mock translation!");

            return "redirect:/projects/" + projectId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Error completing project: " + e.getMessage());
            return "redirect:/projects/" + projectId;
        }
    }

    /**
     * Creates a mock translator user for testing purposes
     */
    private User createMockTranslator() {
        // This is a mock implementation - in a real scenario, you'd fetch or create a real user
        return User.createTranslator(
                "Mock Translator",
                "translator@mock.com",
                Collections.emptySet()
        );
    }

    /**
     * Creates mock translated file content
     */
    private byte[] createMockTranslatedFile(Locale targetLanguage) {
        String mockContent = String.format(
                "Mock Translation\n" +
                "================\n\n" +
                "This is a mock translated document in %s (%s).\n\n" +
                "The translation was completed by our automated mock system.\n" +
                "In a real scenario, this would contain the actual translated content.\n\n" +
                "Generated at: %s\n",
                targetLanguage.getDisplayLanguage(),
                targetLanguage.getLanguage(),
                java.time.Instant.now()
        );

        return mockContent.getBytes(StandardCharsets.UTF_8);
    }

    private static ProjectVO mapToProjectVO(Project project) {
        UserVO customerVO = project.getCustomer() != null ? mapToUserVO(project.getCustomer()) : null;
        UserVO translatorVO = project.getTranslator() != null ? mapToUserVO(project.getTranslator()) : null;

        return new ProjectVO(
                project.getId(),
                customerVO,
                translatorVO,
                project.getTargetLanguage(),
                project.getState() != null ? project.getState().name() : "UNKNOWN",
                project.getCreatedAt(),
                project.getSourceFile() != null && project.getSourceFile().length > 0,
                project.getTranslatedFile() != null && project.getTranslatedFile().length > 0
        );
    }

    private static UserVO mapToUserVO(User user) {
        return new UserVO(
                user.getId(),
                user.getName(),
                user.getEmailAddress(),
                user.getRole() != null ? user.getRole().name() : "UNKNOWN",
                user.getLanguages(),
                user.getCreatedAt()
        );
    }
}

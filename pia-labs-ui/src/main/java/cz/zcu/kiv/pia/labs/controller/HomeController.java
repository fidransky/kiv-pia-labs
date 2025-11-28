package cz.zcu.kiv.pia.labs.controller;

import cz.zcu.kiv.pia.labs.controller.vo.CreateProjectVO;
import cz.zcu.kiv.pia.labs.controller.vo.ProjectVO;
import cz.zcu.kiv.pia.labs.controller.vo.UserVO;
import cz.zcu.kiv.pia.labs.domain.Project;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

@Controller
public class HomeController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public String home(Model model) {
        List<Project> projects;
        try {
            projects = projectService.getAllProjects();
        } catch (AccessDeniedException e) {
            projects = Collections.emptyList();
        }

        var projectVOs = projects.stream()
                .map(HomeController::mapToProjectVO)
                .toList();

        model.addAttribute("projects", projectVOs);
        model.addAttribute("createProjectVO", new CreateProjectVO(null, null));
        model.addAttribute("availableLanguages", getAvailableLanguages());

        return "home";
    }

    @PostMapping("/projects")
    public String createProject(@ModelAttribute CreateProjectVO createProjectVO, 
                              RedirectAttributes redirectAttributes) {
        try {
            // validate inputs
            if (createProjectVO.targetLanguage() == null || createProjectVO.targetLanguage().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Target language is required");
                return "redirect:/";
            }

            if (createProjectVO.sourceFile() == null || createProjectVO.sourceFile().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Source file is required");
                return "redirect:/";
            }

            // convert inputs
            byte[] sourceFileBytes = createProjectVO.sourceFile().getBytes();
            Locale targetLanguage = createProjectVO.getTargetLanguageAsLocale();

            // create translation project
            Project newProject = projectService.createProject(targetLanguage, sourceFileBytes);

            redirectAttributes.addFlashAttribute("successMessage", "Project created successfully with ID: " + newProject.getId());

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating project: " + e.getMessage());
        }

        return "redirect:/";
    }

    private static List<LanguageOption> getAvailableLanguages() {
        String[] languageCodes = {"en", "es", "fr", "de", "it", "pt", "ru", "ja", "ko", "zh"};

        return Stream.of(languageCodes)
                .map(code -> {
                    Locale locale = Locale.forLanguageTag(code);
                    String displayName = locale.getDisplayLanguage(Locale.ENGLISH);
                    return new LanguageOption(code, displayName);
                })
                .toList();
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

    // Helper record for language options
    public record LanguageOption(String code, String name) {}
}

package cz.zcu.kiv.pia.labs.controller;

import cz.zcu.kiv.pia.labs.domain.Location;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.domain.UserRole;
import cz.zcu.kiv.pia.labs.service.DamageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Instant;
import java.util.UUID;

@Controller
public class HomeController {
    @Autowired
    private DamageService damageService;

    @GetMapping
    public String listReportedDamage(Model model) {
        model.addAttribute("damageList", damageService.retrieveReportedDamage());
        model.addAttribute("newDamage", new DamageDTO(""));

        return "home";
    }

    @PostMapping
    public String createDamage(@ModelAttribute DamageDTO damageDTO) {
        var impaired = new User(UUID.randomUUID(), "Jane Doe", "jane.doe@example.com", UserRole.IMPAIRED);
        var timestamp = Instant.now();
        var location = new Location("49.7269708", "13.3516872");
        var description = damageDTO.description();

        damageService.create(impaired, timestamp, location, description);

        return "redirect:/";
    }

    public record DamageDTO(String description) {}
}

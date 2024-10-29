package cz.zcu.kiv.pia.labs.controller;

import cz.zcu.kiv.pia.labs.service.DamageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private DamageService damageService;

    @GetMapping
    public String listReportedDamage(Model model) {
        model.addAttribute("damageList", damageService.retrieveReportedDamage());

        return "home";
    }
}

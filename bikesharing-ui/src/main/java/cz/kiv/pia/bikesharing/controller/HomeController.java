package cz.kiv.pia.bikesharing.controller;

import cz.kiv.pia.bikesharing.service.StandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final StandService standService;

    public HomeController(StandService standService) {
        this.standService = standService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("stands", standService.getAll());

        return "home";
    }
}

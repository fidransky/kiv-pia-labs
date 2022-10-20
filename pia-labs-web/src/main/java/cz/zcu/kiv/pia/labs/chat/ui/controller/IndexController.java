package cz.zcu.kiv.pia.labs.chat.ui.controller;

import cz.zcu.kiv.pia.labs.chat.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Note that we're not using @RestController here
@Controller
public final class IndexController extends AbstractController {
    // Autowire RoomService using constructor-based dependency injection
    private final RoomService roomService;

    public IndexController(RoomService roomService) {
        this.roomService = roomService;
    }

    // Accept query parameter "q" used for searching chat rooms, submitted from navbar search form (see layout.html)
    @GetMapping
    public String index(@RequestParam(name = "q", required = false) String query, Model model) {
        model.addAttribute("query", query);

        var rooms = roomService.searchRooms(query);
        // Flux will be resolved before rendering.
        model.addAttribute("rooms", rooms);

        // Return the template name (templates/index.html)
        return "index";
    }
}

package cz.zcu.kiv.pia.labs.chat.ui.controller;

import cz.zcu.kiv.pia.labs.chat.service.RoomService;
import cz.zcu.kiv.pia.labs.chat.service.UserService;
import cz.zcu.kiv.pia.labs.chat.ui.vo.RoomVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

// Note that we're not using @RestController here
@Controller
public final class CreateRoomController extends AbstractController {
    // Autowire RoomService using constructor-based dependency injection
    private final RoomService roomService;

    public CreateRoomController(RoomService roomService, UserService userService) {
        super(userService);
        this.roomService = roomService;
    }

    @GetMapping("/room/create")
    public String createRoom(Model model) {
        model.addAttribute("roomVO", new RoomVO(""));

        // Return the template name (templates/createRoom.html)
        return "createRoom";
    }

    @PostMapping("/room/create")
    public Mono<String> createRoom(@ModelAttribute RoomVO roomVO, BindingResult errors, Model model) {
        return userService.getCurrentUser()
                .flatMap(user -> roomService.createRoom(roomVO.name(), user))
                .map(room -> "redirect:/room/" + room.getId()); // Redirect to room view
    }
}

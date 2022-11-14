package cz.zcu.kiv.pia.labs.chat.ui.controller;

import cz.zcu.kiv.pia.labs.chat.domain.Message;
import cz.zcu.kiv.pia.labs.chat.service.RoomService;
import cz.zcu.kiv.pia.labs.chat.service.UserService;
import cz.zcu.kiv.pia.labs.chat.ui.vo.MessageVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

// Note that we're not using @RestController here
@Controller
public final class ViewRoomController extends AbstractController {
    // Autowire RoomService using constructor-based dependency injection
    private final RoomService roomService;

    public ViewRoomController(RoomService roomService, UserService userService) {
        super(userService);
        this.roomService = roomService;
    }

    @GetMapping("/room/{roomId}")
    public String viewRoom(@PathVariable UUID roomId, Model model) {
        var room = roomService.getRoom(roomId);
        // Mono will be resolved before rendering.
        model.addAttribute("room", room);

        var messages = roomService.getRoomMessages(roomId);
        // Create a data-driven wrapper around Flux that sets Thymeleaf to use data-driven mode,
        // rendering HTML (iterations) as items are produced in a reactive-friendly manner.
        // This object also makes Spring WebFlux avoid trying to resolve the Flux completely before
        // rendering the HTML.
        var messagesDriver = new ReactiveDataDriverContextVariable(messages);
        model.addAttribute("messages", messagesDriver);

        // Add empty message VO to the model.
        model.addAttribute("messageVO", new MessageVO(""));

        // Return the template name (templates/viewRoom.html)
        return "viewRoom";
    }

    @PostMapping("/room/{roomId}/messages")
    public Mono<String> sendMessage(@PathVariable UUID roomId, @ModelAttribute MessageVO messageVO) {
        return userService.getCurrentUser()
                .map(user -> new Message(UUID.randomUUID(), messageVO.text(), Instant.now(), user))
                .flatMap(message -> roomService.sendMessageToRoom(roomId, message))
                .map(room -> "redirect:/room/" + room.getId());
    }
}

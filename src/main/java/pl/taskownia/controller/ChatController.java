package pl.taskownia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.taskownia.model.Chat;
import pl.taskownia.service.ChatService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/get-last")
    public Page<Chat> getLastChats(@RequestParam(required = false, defaultValue = "10") Integer howMany) {
        return chatService.getLastChat(howMany);
    }

    @GetMapping("/all")
    public List<Chat> getAll() {
        return chatService.getAll();
    }

    @PostMapping("/sent")
    public ResponseEntity<?> sentMessage(HttpServletRequest r, @RequestParam String msg) {
        return chatService.sent(r, msg);
    }
}

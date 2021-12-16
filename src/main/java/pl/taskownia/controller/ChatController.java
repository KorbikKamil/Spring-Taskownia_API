package pl.taskownia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get last messages from global chat.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found messages",
                    content = {@Content(mediaType = "application/json")}),
    })
    @GetMapping("/get-last")
    public Page<Chat> getLastChats(@Parameter(description = "Amount of messages.", in = ParameterIn.QUERY, example = "10")
                                   @RequestParam(required = false, defaultValue = "10") Integer howMany) {
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

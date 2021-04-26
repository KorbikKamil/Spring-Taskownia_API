package pl.taskownia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.taskownia.model.Chat;
import pl.taskownia.model.User;
import pl.taskownia.repository.ChatRepository;
import pl.taskownia.repository.UserRepository;
import pl.taskownia.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<?> sent(HttpServletRequest r, String msg) {
        User u = userRepository.findByUsername(jwtTokenProvider.getLogin(jwtTokenProvider.resolveToken(r)));

        Chat chat = new Chat();
        chat.setUser(u);
        chat.setMessage(msg);
        chat.setDate(new Date(System.currentTimeMillis()));
        chatRepository.save(chat);
        return new ResponseEntity<>(chatRepository.findAll() ,HttpStatus.OK);
    }

    public Page<Chat> getLastChat(Integer howMany) {
        Page<Chat> chatPage = chatRepository.findAll(
                PageRequest.of(0, howMany, Sort.by(Sort.Direction.DESC, "id")));
        return chatPage;
    }

    public List<Chat> getAll(){
        return chatRepository.findAll();
    }
}

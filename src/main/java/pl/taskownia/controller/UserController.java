package pl.taskownia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.taskownia.data.UserDataUpdate;
import pl.taskownia.model.Review;
import pl.taskownia.model.User;
import pl.taskownia.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password) {
        return userService.login(username, password);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user, @RequestParam(required = false, name = "captcha") String captchaResponse) {
        return userService.register(user, captchaResponse);
    }

    @PostMapping("/change-pwd")
    public ResponseEntity<?> chgPwd(HttpServletRequest r, @RequestParam String oldPass,
                                    @RequestParam String newPass) {
        return userService.chgPass(r, oldPass, newPass);
    }

    @PostMapping("/change-pwd-admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> chgPwdAdm(HttpServletRequest r, @RequestParam String username,
                                       @RequestParam String newPass) {
        return userService.chgPassAdmin(r, username, newPass);
    }

    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')") //TODO: uncomment, but not working properly with ADMIN
    public List<User> showAll() {
        return userService.showAll();
    }

    @GetMapping("/me")
    public User whoami(HttpServletRequest r) {
        return userService.whoami(r);
    }

    @GetMapping("/view/{id}") //FIXME: check if works
    public User getOtherUserData(@PathVariable Long id) {
        return userService.getOtherUserData(id);
    }

    @GetMapping("/view")
    public User getOtherUserDataByUsername(@RequestParam String username) {
        return userService.getOtherUserDataByUsername(username);
    }

    @GetMapping("/registration-confirm")
    public ResponseEntity<?> confirmRegistration(@RequestParam String token) {
        return userService.confirmRegistration(token);
    }

    /*
    request.data[]
    name, email, first_name, last_name, phone, birth_date, city, state, country, zip_code

    personal_datas: first_name, last_name, phone, birth_date (+updated_at)
    image: image_path
    addresses: city, country, zip_code, state

     */
    //TODO: change personal image here or other request? For me other request
    @PostMapping("/update-data")
    public User updateData(HttpServletRequest r, @RequestBody UserDataUpdate userDataUpdate) {
        return userService.updateData(r, userDataUpdate); //FIXME zrobic
    }

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(HttpServletRequest r, @RequestParam("image") MultipartFile multipartFile) {
        System.out.println("image");
        return userService.uploadImage(r, multipartFile);
    }

    @PostMapping("/inuse")
    public ResponseEntity<?> isInUse(@RequestParam(required = false) String username,
                                     @RequestParam(required = false) String email) {
        return userService.isInUse(username, email);
    }

    @PostMapping("/review/add")
    public ResponseEntity<?> addReview(HttpServletRequest r, @RequestBody Review review) {
        return userService.addReview(r, review);
    }
}

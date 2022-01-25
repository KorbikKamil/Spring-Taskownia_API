package pl.taskownia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.taskownia.data.ChangePasswordData;
import pl.taskownia.data.UserCredentials;
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
    public ResponseEntity<?> login(@RequestBody UserCredentials userCredentials) {
        return userService.login(userCredentials.getUsername(), userCredentials.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user, @RequestParam(required = false, name = "captcha") String captchaResponse) {
        return userService.register(user, captchaResponse);
    }

    @PostMapping("/change-pwd")
    public ResponseEntity<?> chgPwd(HttpServletRequest r, @RequestBody ChangePasswordData changePasswordData) {
        return userService.chgPass(r, changePasswordData.getOldPassword(), changePasswordData.getNewPassword());
    }

    @PostMapping("/change-pwd-admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> chgPwdAdm(HttpServletRequest r, @RequestBody UserCredentials userCredentials) {
        return userService.chgPassAdmin(r, userCredentials.getUsername(), userCredentials.getPassword());
    }

    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> showAll() {
        return userService.showAll();
    }

    @GetMapping("/me")
    public User whoami(HttpServletRequest r) {
        return userService.whoami(r);
    }

    @GetMapping("/view/{id}")
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

    @PostMapping("/update-data")
    public User updateData(HttpServletRequest r, @RequestBody UserDataUpdate userDataUpdate) {
        return userService.updateData(r, userDataUpdate);
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

package pl.taskownia.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import pl.taskownia.captcha.ReCaptchaResponse;
import pl.taskownia.data.UserDataUpdate;
import pl.taskownia.event.OnRegistrationEvent;
import pl.taskownia.model.*;
import pl.taskownia.repository.AccountConfirmationTokenRepository;
import pl.taskownia.repository.ReviewRepository;
import pl.taskownia.repository.UserRepository;
import pl.taskownia.security.JwtTokenProvider;
import pl.taskownia.utils.FileUploadUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AccountConfirmationTokenRepository accountConfirmationTokenRepository;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<?> login(String uname, String pass) {
        try {
            User u = userRepository.findByUsername(uname);
            if (u.getEnabled() == false)
                return new ResponseEntity<>("Account not enabled!", HttpStatus.NOT_ACCEPTABLE); //FIXME KORBAS USTAW LEPSZY KOD
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(uname, pass));
            String token = jwtTokenProvider.createToken(u.getId(),
                    uname, u.getRoles());
            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>("Invalid login or password!", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> register(User u, String captchaResponse) {
//        String captchaUrl = "https://www.google.com/recaptcha/api/siteverify";
//        String captchaParams = "?secret=6Ld8cfYaAAAAACX_Cd8_jiaGIkC8F1lCBJlqHIek&response=" + captchaResponse;
//
//        ReCaptchaResponse reCaptchaResponse = restTemplate.exchange(captchaUrl + captchaParams, HttpMethod.POST, null, ReCaptchaResponse.class).getBody();
//        if (reCaptchaResponse != null && !reCaptchaResponse.isSuccess()) {
//            return new ResponseEntity<>("Captcha is invalid", HttpStatus.CONFLICT);
//        }
        if (userRepository.findByEmail(u.getEmail()) != null) {
            return new ResponseEntity("Email is taken", HttpStatus.CONFLICT);
        }
        if (u.getRoles().contains(Role.ROLE_ADMIN)) {
            return new ResponseEntity("Cannot use admin role", HttpStatus.CONFLICT);
        }
        if (!userRepository.existsByUsername(u.getUsername())) {
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            u.setMakerStatus(User.MakerStatus.NEUTRAL);
            u.setEnabled(false);
            u.setCreatedAt(new Date(System.currentTimeMillis()));
            u.setUpdatedAt(new Date(System.currentTimeMillis()));
            userRepository.save(u);
            System.out.println("1");
            try {
                applicationEventPublisher.publishEvent(new OnRegistrationEvent(u));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("2");
            //return new ResponseEntity(jwtTokenProvider.createToken(u.getId(), u.getUsername(), u.getRoles()), HttpStatus.CREATED);
            return new ResponseEntity("Account created, confirm email address", HttpStatus.CREATED);
        } else {
            //TODO if acc is not enabled, then register success
            return new ResponseEntity("Username is taken", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> confirmRegistration(String token) {
        try {
            AccountConfirmationToken act = accountConfirmationTokenRepository.findByToken(token);
            if (act == null)
                return new ResponseEntity<>("Nie odnaleziono tokenu!", HttpStatus.CONFLICT);
            if (act.getUser().getEnabled())
                return new ResponseEntity<>("Uzytkownik już aktywowany!", HttpStatus.CONFLICT);
            Date actualDate = new Date(System.currentTimeMillis());
            if (actualDate.after(act.getExpiry()))
                return new ResponseEntity<>("Token przedawniony!", HttpStatus.CONFLICT);
            act.getUser().setEnabled(true);
            accountConfirmationTokenRepository.save(act);
            return ResponseEntity.ok("Pomyślnie aktywowano konto, proszę sie zalogować!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Cos poszlo zle", HttpStatus.BAD_REQUEST); //FIXME KORBAS KODZIK
        }
    }

    public ResponseEntity<?> chgPass(HttpServletRequest r, String oldPass, String newPass) {
        if (oldPass.equals(newPass)) {
            return new ResponseEntity<>("Old and new pass is same!", HttpStatus.CONFLICT);
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtTokenProvider.getLogin(jwtTokenProvider.resolveToken(r)), oldPass));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid old password!", HttpStatus.CONFLICT);
        }
        User u = this.whoami(r);
        u.setPassword(passwordEncoder.encode(newPass));
        u.setUpdatedAt(new Date(System.currentTimeMillis()));
        userRepository.save(u);
        return ResponseEntity.ok().build();
//        return jwtTokenProvider.createToken(u.getId(), u.getUsername());
    }

    public ResponseEntity<?> chgPassAdmin(HttpServletRequest r, String username, String newPass) {
        User u = userRepository.findByUsername(username);
        u.setPassword(passwordEncoder.encode(newPass));
        u.setUpdatedAt(new Date(System.currentTimeMillis()));
        userRepository.save(u);
        return ResponseEntity.ok().build();
    }

    public void delete(String uname) {
        userRepository.deleteByUsername(uname);
    }

    public ResponseEntity<?> search(String uname) {
        User u = userRepository.findByUsername(uname);

        if (u == null) {
            u = userRepository.findByEmail(uname);
        }
        if (u == null) {
            return new ResponseEntity<>("User doesn't exists!", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    public List<User> showAll() {
        return userRepository.findAll();
    }

    public User whoami(HttpServletRequest r) {
        return userRepository.findByUsername(jwtTokenProvider.getLogin(jwtTokenProvider.resolveToken(r)));
    }

    public User getOtherUserData(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getOtherUserDataByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateData(HttpServletRequest request, UserDataUpdate userDataUpdate) {
        User u = userRepository.findByUsername(jwtTokenProvider.getLogin(jwtTokenProvider.resolveToken(request)));
        UserAddress userAddress = u.getAddress();
        UserPersonalData userPersonalData = u.getPersonalData();

        if (u.getRoles().contains(Role.ROLE_CLIENT_AUTHOR)) {
            u.setMakerStatus(User.MakerStatus.NEUTRAL);
        }
        if (u.getRoles().contains(Role.ROLE_CLIENT_MAKER) && userDataUpdate.getMakerStatus() != null) {
            u.setMakerStatus(userDataUpdate.getMakerStatus());
        }

        if (userDataUpdate.getEmail() != null)
            u.setEmail(userDataUpdate.getEmail());
        if (userDataUpdate.getFirstName() != null)
            userPersonalData.setFirstName(userDataUpdate.getFirstName());
        if (userDataUpdate.getLastName() != null)
            userPersonalData.setLastName(userDataUpdate.getLastName());
        if (userDataUpdate.getPhone() != null)
            userPersonalData.setPhone(userDataUpdate.getPhone());
        if (userDataUpdate.getBirthDate() != null)
            userPersonalData.setBirthDate(userDataUpdate.getBirthDate());
        if (userDataUpdate.getCity() != null)
            userAddress.setCity(userDataUpdate.getCity());
        if (userDataUpdate.getState() != null)
            userAddress.setState(userDataUpdate.getState());
        if (userDataUpdate.getCountry() != null)
            userAddress.setCountry(userDataUpdate.getCountry());
//        if (userDataUpdate.getZipCode() != null)
//            userAddress.setZipCode(userDataUpdate.getZipCode());

        userRepository.save(u);
        return u;
    }

    public ResponseEntity<?> uploadImage(HttpServletRequest request, MultipartFile multipartFile) {
        User u = userRepository.findByUsername(jwtTokenProvider.getLogin(jwtTokenProvider.resolveToken(request)));

        String fileExtension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String newFilename = UUID.randomUUID().toString() + '.' + fileExtension;

        u.getImage().setImage_path(newFilename); //TODO: verify
        userRepository.save(u);
        if (FileUploadUtil.uploadFile(newFilename, multipartFile)) {
            return ResponseEntity.ok("Image uploaded");
        } else {
            return new ResponseEntity<>("Upload failed!", HttpStatus.CONFLICT);
        }

    }

    public ResponseEntity<?> isInUse(String username, String email) {
        if (userRepository.findByEmail(email) != null || userRepository.findByUsername(username) != null) {
            return new ResponseEntity<>("Email or/and username are taken.", HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok().build();
    }


    public String refresh(String uname) {
        User u = userRepository.findByUsername((uname));
        return jwtTokenProvider.createToken(u.getId(), u.getUsername(), u.getRoles());
    }

    public void createConfirmationToken(User user, String token) {
        AccountConfirmationToken myToken = new AccountConfirmationToken(user, token);
        accountConfirmationTokenRepository.save(myToken);
    }

    public ResponseEntity<?> addReview(HttpServletRequest request, Review review) {
        User u = userRepository.findByUsername(jwtTokenProvider.getLogin(jwtTokenProvider.resolveToken(request)));
        User rev = userRepository.findById(review.getReviewedId()).orElse(null);
        if(rev==null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        review.setAuthor(u);
        review.setReviewed(rev);
        review.setCreatedAt(new Date(System.currentTimeMillis()));
        review.setUpdatedAt(new Date(System.currentTimeMillis()));
        reviewRepository.save(review);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

package pl.taskownia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.taskownia.data.UserDataUpdate;
import pl.taskownia.model.Role;
import pl.taskownia.model.User;
import pl.taskownia.model.UserAddress;
import pl.taskownia.model.UserPersonalData;
import pl.taskownia.repository.UserRepository;
import pl.taskownia.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<?> login(String uname, String pass) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(uname, pass));
            String token = jwtTokenProvider.createToken(userRepository.findByUsername(uname).getId(),
                    uname, userRepository.findByUsername((uname)).getRoles());
            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>("Invalid login or password!", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> register(User u) {
        if (userRepository.findByEmail(u.getEmail()) != null) {
            return new ResponseEntity("Email is taken", HttpStatus.CONFLICT);
        }
        if (u.getRoles().contains(Role.ROLE_ADMIN)) {
            return new ResponseEntity("Cannot use admin role", HttpStatus.CONFLICT);
        }
        if (!userRepository.existsByUsername(u.getUsername())) {
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            u.setMakerStatus(User.MakerStatus.NEUTRAL);
            u.setCreated_at(new Date(System.currentTimeMillis()));
            u.setUpdated_at(new Date(System.currentTimeMillis()));
            userRepository.save(u);
            return ResponseEntity.ok(jwtTokenProvider.createToken(u.getId(), u.getUsername(), u.getRoles()));
        } else {
            return new ResponseEntity("Username is taken", HttpStatus.CONFLICT);
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
        u.setUpdated_at(new Date(System.currentTimeMillis()));
        userRepository.save(u);
        return ResponseEntity.ok().build();
//        return jwtTokenProvider.createToken(u.getId(), u.getUsername());
    }

    public ResponseEntity<?> chgPassAdmin(HttpServletRequest r, String username, String newPass) {
        User u = userRepository.findByUsername(username);
        u.setPassword(passwordEncoder.encode(newPass));
        u.setUpdated_at(new Date(System.currentTimeMillis()));
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
}

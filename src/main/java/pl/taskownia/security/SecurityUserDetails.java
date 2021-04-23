package pl.taskownia.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.taskownia.model.User;
import pl.taskownia.repository.UserRepository;

@Service
public class SecurityUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String uname) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(uname);

        if(user == null) {
            throw new UsernameNotFoundException("User not found! Username: "+uname);
        }

        return org.springframework.security.core.userdetails.User.withUsername(uname)
                .password(user.getPassword())
                .authorities(user.getRoles())
                .accountExpired(false) //TODO: check rewrite
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}

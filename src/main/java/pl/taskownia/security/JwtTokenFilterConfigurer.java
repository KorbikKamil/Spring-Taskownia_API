package pl.taskownia.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    //TODO: rewrite
    private JwtTokenProvider jwtTokProv;

    public JwtTokenFilterConfigurer(JwtTokenProvider jwtTokProv) {
        this.jwtTokProv = jwtTokProv;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtTokenFilter custFilt = new JwtTokenFilter(jwtTokProv);
        http.addFilterBefore(custFilt, UsernamePasswordAuthenticationFilter.class);
    }
}

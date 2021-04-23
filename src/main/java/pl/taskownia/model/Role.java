package pl.taskownia.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_CLIENT_AUTHOR, ROLE_CLIENT_MAKER;

    public String getAuthority() {
        return name();
    }
}

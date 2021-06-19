package pl.taskownia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountConfirmationToken {
    @Transient
    private final int expirationTime = 1 * 24 * 60; //TODO remove from entity

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date expiry;

    public AccountConfirmationToken(User user, String token) {
        this.user = user;
        this.token = token;

        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        c.add(Calendar.MINUTE, expirationTime);

        this.expiry = c.getTime();
    }
}

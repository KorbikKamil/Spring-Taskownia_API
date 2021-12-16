package pl.taskownia.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.taskownia.serializer.UserSerializer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@JsonSerialize(using = UserSerializer.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @ElementCollection(fetch = FetchType.EAGER) //Required by Spring Security
    List<Role> roles;

    @NotNull
    private MakerStatus makerStatus;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_data_id", referencedColumnName = "id_pers_data")
    private UserPersonalData personalData = new UserPersonalData();

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id_address")
    private UserAddress address = new UserAddress();

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id_image")
    private UserImage image = new UserImage();

    @OneToMany(mappedBy = "user")
    private List<Chat> chat = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Project> projectsAuthor = new ArrayList<>();

    @OneToMany(mappedBy = "maker")
    private List<Project> projectsMaker = new ArrayList<>();

    @OneToMany(mappedBy = "reviewed")
    private List<Review> myReviews = new ArrayList<>();

    @NotNull
    private Boolean enabled;

    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;

    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedAt;

    public enum MakerStatus {
        NOT_READY, NEUTRAL, READY
    }
}

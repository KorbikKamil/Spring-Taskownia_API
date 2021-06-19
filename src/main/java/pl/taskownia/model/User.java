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
    //    @Column
//    private AppRole appRole;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @ElementCollection(fetch = FetchType.EAGER) //Required by Spring Security
    List<Role> roles;
    @Column(nullable = false)
    private MakerStatus makerStatus;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_data_id", referencedColumnName = "id_pers_data")
    @NotNull
    private UserPersonalData personalData = new UserPersonalData();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id_address")
    @NotNull
    private UserAddress address = new UserAddress();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id_image")
    @NotNull
    private UserImage image = new UserImage();
    @OneToMany(mappedBy = "user")
    private List<Chat> chat = new ArrayList<>();
    @OneToMany(mappedBy = "author")
    private List<Project> projectsAuthor = new ArrayList<>();
    @OneToMany(mappedBy = "maker")
    private List<Project> projectsMaker = new ArrayList<>();
    @OneToMany(mappedBy = "reviewed")
    private List<Review> myReviews = new ArrayList<>();
    //    @ManyToMany
//    @JoinTable(
//            name = "project_interest",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "project_id"))
//    private List<Project> projectInterests = new ArrayList<>();
    @Column(nullable = false)
    private Boolean enabled;
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedAt;
//TODO: add relation for projectInterest
//    private enum AppRole {
//        AUTHOR, MAKER
//    }

    public enum MakerStatus {
        NOT_READY, NEUTRAL, READY
    }
}

package pl.taskownia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.NotNull;
import pl.taskownia.serializer.UserSerializer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
//    @ManyToMany
//    @JoinTable(
//            name = "project_interest",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "project_id"))
//    private List<Project> projectInterests = new ArrayList<>();
    @Column(nullable = false)
    @Temporal(value= TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(nullable = false)
    @Temporal(value= TemporalType.TIMESTAMP)
    private Date updatedAt;
//TODO: add relation for projectInterest
//    private enum AppRole {
//        AUTHOR, MAKER
//    }

    public enum MakerStatus {
        NOT_READY, NEUTRAL, READY
    }

    public User(){}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

//    public AppRole getAppRole() {
//        return appRole;
//    }
//
//    public void setAppRole(AppRole role) {
//        this.appRole = role;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public MakerStatus getMakerStatus() {
        return makerStatus;
    }

    public void setMakerStatus(MakerStatus makerStatus) {
        this.makerStatus = makerStatus;
    }

    public List<Chat> getChat() {
        return chat;
    }

    public void setChat(List<Chat> chat) {
        this.chat = chat;
    }

    public UserPersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(UserPersonalData personalData) {
        this.personalData = personalData;
    }

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }

    public UserImage getImage() {
        return image;
    }

    public void setImage(UserImage image) {
        this.image = image;
    }

    public List<Chat> getPub_msg() {
        return chat;
    }

    public void setPub_msg(List<Chat> pub_msg) {
        this.chat = chat;
    }

    public List<Project> getProjectsAuthor() {
        return projectsAuthor;
    }

    public void setProjectsAuthor(List<Project> projectsAuthor) {
        this.projectsAuthor = projectsAuthor;
    }

    public List<Project> getProjectsMaker() {
        return projectsMaker;
    }

    public void setProjectsMaker(List<Project> projectsMaker) {
        this.projectsMaker = projectsMaker;
    }

//    public List<Project> getProjectInterests() {
//        return projectInterests;
//    }
//
//    public void setProjectInterests(List<Project> projectInterests) {
//        this.projectInterests = projectInterests;
//    }

    public Date getCreated_at() {
        return createdAt;
    }

    public void setCreated_at(Date created_at) {
        this.createdAt = created_at;
    }

    public Date getUpdated_at() {
        return updatedAt;
    }

    public void setUpdated_at(Date updated_at) {
        this.updatedAt = updated_at;
    }
}

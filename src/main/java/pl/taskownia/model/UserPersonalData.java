package pl.taskownia.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "personal_datas")
public class UserPersonalData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pers_data")
    private Long id;
    @OneToOne(mappedBy = "personalData")
    private User user;
    @Column(nullable = true)
    private String firstName;
    @Column(nullable = true)
    private String lastName;
    @Column(nullable = true)
    private String phone;
    @Column(nullable = true)
    private String birthDate;
//    @Column(nullable = true) //TODO: no needed - will use User.updated_at
//    @Temporal(value= TemporalType.TIMESTAMP)
//    private Date created_at;
//    @Column(nullable = true)
//    @Temporal(value= TemporalType.TIMESTAMP)
//    private Date updated_at;

    public UserPersonalData() {
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

//    public Date getCreated_at() {
//        return created_at;
//    }
//
//    public void setCreated_at(Date created_at) {
//        this.created_at = created_at;
//    }
//
//    public Date getUpdated_at() {
//        return updated_at;
//    }
//
//    public void setUpdated_at(Date updated_at) {
//        this.updated_at = updated_at;
//    }
}

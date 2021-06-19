package pl.taskownia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
}

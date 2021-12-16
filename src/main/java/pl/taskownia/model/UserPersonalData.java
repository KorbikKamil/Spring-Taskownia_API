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

    private String firstName;

    private String lastName;

    private String phone;

    private String birthDate;
}

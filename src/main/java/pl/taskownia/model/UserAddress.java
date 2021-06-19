package pl.taskownia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    private Long id;
    @OneToOne(mappedBy = "address")
    private User user;
    @Column(nullable = true)
    private String city;
    @Column(nullable = true)
    private String state;
    @Column(nullable = true)
    private String country;
//    @Column(nullable = true)
//    private String zipCode;
//    @Column(nullable = true) //TODO: no needed - will use User.updated_at
//    @Temporal(value= TemporalType.TIMESTAMP)
//    private Date created_at;
//    @Column(nullable = true)
//    @Temporal(value= TemporalType.TIMESTAMP)
//    private Date updated_at;
}

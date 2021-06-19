package pl.taskownia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "images")
public class UserImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image")
    private Long id;
    @OneToOne(mappedBy = "image")
    private User user;
    @Column(nullable = true)
    private String image_path;
//    @Column(nullable = true) //TODO: no needed - will use User.updated_at
//    @Temporal(value= TemporalType.TIMESTAMP)
//    private Date created_at;
//    @Column(nullable = true)
//    @Temporal(value= TemporalType.TIMESTAMP)
//    private Date updated_at;
}

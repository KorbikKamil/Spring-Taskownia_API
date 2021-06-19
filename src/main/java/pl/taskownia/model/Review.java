package pl.taskownia.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.taskownia.serializer.ProjectSerializer;
import pl.taskownia.serializer.ReviewSerializer;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews")
@JsonSerialize(using = ReviewSerializer.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_review")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToOne
    @JoinColumn(name = "reviewed_id")
    private User reviewed;
    @Column(nullable = false, length = 10000)
    private String comment;
    @Column(nullable = false)
    private Integer rating;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Transient
    private Long reviewedId;
}

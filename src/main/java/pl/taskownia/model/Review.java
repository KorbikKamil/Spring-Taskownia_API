package pl.taskownia.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.taskownia.serializer.ReviewSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull
    @Size(max = 10000)
    private String comment;

    @NotNull
    private Integer rating;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;

    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Transient
    private Long reviewedId;
}

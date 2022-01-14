package pl.taskownia.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.taskownia.serializer.ProjectSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
@JsonSerialize(using = ProjectSerializer.class)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_project")
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Size(max = 10000)
    private String description;

    @NotNull
    private ProjectStatus projectStatus;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(mappedBy = "project")
    @JsonProperty("project_messages")
    private List<ProjectChat> messages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "maker_id")
    private User maker;

    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;

    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_review_id", referencedColumnName = "id")
    private ProjectReview projectReview;

    public enum ProjectStatus {
        NEW, IN_PROGRESS, FINISHED
    }
}

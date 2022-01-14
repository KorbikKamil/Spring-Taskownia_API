package pl.taskownia.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.taskownia.serializer.ProjectReviewSerializer;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project_reviews")
@JsonSerialize(using = ProjectReviewSerializer.class)
public class ProjectReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "projectReview")
    private Project project;

    private ProjectRating rating;

    private String review;

    public enum ProjectRating {
        EXCELLENT(5), VERY_GOOD(4), GOOD(3), BAD(2), VERY_BAD(1), WORST(0);

        private final Integer point;

        ProjectRating(Integer point) {
            this.point = point;
        }

        public Integer getPoint() {
            return point;
        }

        @JsonCreator
        public static ProjectRating getProjectRatingFromPoint(Integer point) {
            for (ProjectRating projectRating : ProjectRating.values())
                if (projectRating.getPoint().equals(point))
                    return projectRating;
            return null;
        }
    }
}

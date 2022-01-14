package pl.taskownia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.taskownia.model.ProjectReview;

@Repository
public interface ProjectReviewRepository extends JpaRepository<ProjectReview, Long> {
}

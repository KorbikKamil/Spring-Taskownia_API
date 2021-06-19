package pl.taskownia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.taskownia.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}

package pl.taskownia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.taskownia.model.Project;
import pl.taskownia.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByAuthor(User user);
    List<Project> findAllByMaker(User user);
    Optional<Project> findById(Long id);
}

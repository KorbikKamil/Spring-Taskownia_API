package pl.taskownia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.taskownia.model.ProjectChat;

@Repository
public interface ProjectChatRepository extends JpaRepository<ProjectChat, Long> {
}
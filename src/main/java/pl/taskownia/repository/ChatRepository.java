package pl.taskownia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.taskownia.model.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}

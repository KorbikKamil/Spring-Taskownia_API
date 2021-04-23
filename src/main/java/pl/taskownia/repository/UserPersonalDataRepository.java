package pl.taskownia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.taskownia.model.UserPersonalData;

@Repository
public interface UserPersonalDataRepository extends JpaRepository<UserPersonalData, Long> {
}

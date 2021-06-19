package pl.taskownia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.taskownia.model.AccountConfirmationToken;

@Repository
public interface AccountConfirmationTokenRepository extends JpaRepository<AccountConfirmationToken, Long> {
    AccountConfirmationToken findByToken(String token);
}

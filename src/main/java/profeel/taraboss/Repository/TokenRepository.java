package profeel.taraboss.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import profeel.taraboss.Entity.Token;

public interface TokenRepository extends JpaRepository<Token,Long> {
}

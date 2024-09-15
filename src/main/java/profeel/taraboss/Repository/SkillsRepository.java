package profeel.taraboss.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import profeel.taraboss.Entity.Skills;
import profeel.taraboss.Entity.UserProfile;

import java.util.List;

public interface SkillsRepository extends JpaRepository<Skills,Long> {
    List <Skills> findByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM Skills s WHERE s.user.id = :id")
    void deleteByUserId(@Param("id") Long userId);

}

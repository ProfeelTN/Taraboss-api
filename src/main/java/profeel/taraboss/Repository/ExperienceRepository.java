package profeel.taraboss.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import profeel.taraboss.Entity.Education;
import profeel.taraboss.Entity.Experience;
import profeel.taraboss.Entity.Skills;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience,Long> {
    List<Experience> findByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM Experience e WHERE e.user.id = :id")
    void deleteByUserId(@Param("id") Long userId);


}

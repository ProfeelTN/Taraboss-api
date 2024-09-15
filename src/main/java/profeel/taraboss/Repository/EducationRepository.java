package profeel.taraboss.Repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import profeel.taraboss.Entity.Education;
import profeel.taraboss.Entity.Skills;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education,Long> {
    List<Education> findByUserId(Long userId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Education e WHERE e.user.id = :id")
    void deleteByUserId(@Param("id") Long userId);


}

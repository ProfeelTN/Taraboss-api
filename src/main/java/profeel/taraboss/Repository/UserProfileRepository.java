package profeel.taraboss.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import profeel.taraboss.Entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
UserProfile findByUserId(Long userId);
    @Modifying
    @Query("DELETE FROM UserProfile up WHERE up.user.id = :id")
    void deleteByUserId(@Param("id") Long userId);
}

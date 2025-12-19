package umc9th_hackathon.daybreak.domain.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc9th_hackathon.daybreak.domain.mission.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryName(String name);

}

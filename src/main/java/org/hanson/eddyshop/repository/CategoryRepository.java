package org.hanson.eddyshop.repository;

import org.hanson.eddyshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;



public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String category);
    boolean existsByName(String name);
}

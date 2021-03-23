package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category save(Category category);

    Optional<Category> findOneById(Long id);

    @Query(value = "SELECT * FROM category_table c " +
            "WHERE UPPER(c.title) = UPPER(:title)", nativeQuery = true)
    Optional<Category> findOneByTitle(@Param("title") String title);

    @Query(value = "SELECT * FROM category_table c " +
            "ORDER BY c.title ASC", nativeQuery = true)
    List<Category> findAllTitleAsc();

    @Query(value = "SELECT * FROM category_table c " +
            "WHERE UPPER(c.title) LIKE UPPER(CONCAT(:title,'%')) " +
            "ORDER BY c.title ASC", nativeQuery = true)
    List<Category> findAllByParam(@Param("title") String title);

    void deleteById(Long id);
}

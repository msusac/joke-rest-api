package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category save(Category category);

    Category findOneById(Integer id);

    @Query(value = "SELECT * FROM category_table c " +
            "WHERE UPPER(c.name) = UPPER(:name)", nativeQuery = true)
    Category findOneByName(@Param("name") String name);

    @Query(value = "SELECT * FROM category_table " +
            "ORDER BY name ASC", nativeQuery = true)
    List<Category> findAllNameAsc();

    void deleteById(Integer id);
}

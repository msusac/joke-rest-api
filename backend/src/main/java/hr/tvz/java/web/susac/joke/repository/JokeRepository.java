package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.model.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Integer> {

    Joke save(Joke joke);

    Optional<Joke> findOneById(Long id);

    @Query(value = "SELECT * FROM joke_table j " +
            "INNER JOIN category_table c on j.category_id = c.id " +
            "WHERE c.id = :id", nativeQuery = true)
    List<Joke> findAllByCategoryId(@Param("id") Long id);

    @Query(value = "SELECT * FROM joke_table j " +
            "INNER JOIN category_table c on j.category_id = c.id " +
            "WHERE UPPER(c.title) LIKE UPPER(CONCAT(:title,'%'))", nativeQuery = true)
    List<Joke> findAllByParam(@Param("title") String title);

    @Query(value = "SELECT * FROM joke_table j " +
            "INNER JOIN category_table c on j.category_id = c.id " +
            "WHERE UPPER(c.title) LIKE UPPER(CONCAT(:title,'%')) " +
            "ORDER BY j.date_time_created ASC", nativeQuery = true)
    List<Joke> findAllByParamNewest(@Param("title") String title);

    @Query(value = "SELECT * FROM joke_table j " +
            "INNER JOIN category_table c on j.category_id = c.id " +
            "WHERE UPPER(c.title) LIKE UPPER(CONCAT(:title,'%')) " +
            "ORDER BY j.date_time_created DESC", nativeQuery = true)
    List<Joke> findAllByParamOldest(@Param("title") String title);

    @Query(value = "SELECT * FROM joke_table j " +
            "INNER JOIN category_table c on j.category_id = c.id " +
            "WHERE LENGTH(j.description) > 0 " +
            "ORDER BY random() " +
            "LIMIT 5", nativeQuery = true)
    List<Joke> findAllByRandomFive();

    void deleteById(Long id);
}

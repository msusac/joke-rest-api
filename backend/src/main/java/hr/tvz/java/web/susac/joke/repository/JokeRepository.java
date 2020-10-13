package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.model.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Integer> {

    Joke save(Joke joke);

    Joke findOneById(Integer id);

    @Query(value = "SELECT * FROM joke_table j " +
            "ORDER BY j.date_time_created DESC", nativeQuery = true)
    List<Joke> findAllNewest();

    @Query(value = "SELECT j.* FROM joke_table j " +
            "INNER JOIN category_table c on j.category_id = c.id " +
            "LEFT OUTER JOIN rating_table r on r.joke_id = c.id " +
            "WHERE UPPER(c.name) = UPPER(:name) " +
            "GROUP BY j.id " +
            "ORDER BY COUNT(r.joke_id) DESC", nativeQuery = true)
    List<Joke> findAllByCategoryPopular(@Param("name") String name);

    @Query(value = "SELECT j.* FROM joke_table j " +
            "INNER JOIN category_table c on j.category_id = c.id " +
            "LEFT OUTER JOIN rating_table r on r.joke_id = c.id " +
            "WHERE UPPER(c.name) LIKE UPPER(CONCAT(:name,'%')) " +
            "GROUP BY j.id " +
            "ORDER BY COUNT(r.joke_id) DESC", nativeQuery = true)
    List<Joke> findAllByCategoryLikePopular(@Param("name") String name);


    @Query(value = "SELECT j.* FROM joke_table j " +
            "INNER JOIN user_table u on j.user_id = u.id " +
            "LEFT OUTER JOIN rating_table r on r.joke_id = j.id " +
            "WHERE UPPER(u.username) = UPPER(:username) " +
            "GROUP BY j.id " +
            "ORDER BY COUNT(r.joke_id) DESC", nativeQuery = true)
    List<Joke> findAllByUserPopular(@Param("username") String username);

    void deleteById(Integer id);
}

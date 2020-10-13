package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    Rating save(Rating rating);

    @Query(value = "SELECT * FROM rating_table r " +
            "INNER JOIN joke_table j ON r.joke_id = j.id " +
            "INNER JOIN user_table u ON r.user_id = u.id " +
            "WHERE j.id = :id AND u.username = :username", nativeQuery = true)
    Rating findOneByJokeIdAndUsername(@Param("id") Integer id, @Param("username") String username);

    @Query(value = "SELECT * FROM rating_table r " +
            "INNER JOIN joke_table j ON r.joke_id = j.id " +
            "WHERE j.id = :id AND r.type != 'NONE'", nativeQuery = true)
    List<Rating> findAllByJokeId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM rating_table r " +
            "INNER JOIN joke_table j ON r.joke_id = j.id " +
            "WHERE j.id = :id AND r.type = 'LIKED'", nativeQuery = true)
    List<Rating> findAllLikedByJokeId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM rating_table r " +
            "INNER JOIN joke_table j ON r.joke_id = j.id " +
            "WHERE j.id = :id AND r.type = 'FUNNY'", nativeQuery = true)
    List<Rating> findAllFunnyByJokeId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM rating_table r " +
            "INNER JOIN joke_table j ON r.joke_id = j.id " +
            "WHERE j.id = :id AND r.type = 'WOW'", nativeQuery = true)
    List<Rating> findAllWowByJokeId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM rating_table r " +
            "INNER JOIN joke_table j ON r.joke_id = j.id " +
            "WHERE j.id = :id AND r.type = 'NOT_FUNNY'", nativeQuery = true)
    List<Rating> findAllNotFunnyByJokeId(@Param("id") Integer id);
}

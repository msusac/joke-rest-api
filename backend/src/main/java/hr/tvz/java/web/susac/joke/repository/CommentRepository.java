package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Comment save(Comment comment);

    Comment findOneById(Integer id);

    Comment findOneByIdAndJoke_Id(Integer commentId, Integer jokeId);

    @Query(value = "SELECT * FROM comment_table c " +
            "INNER JOIN joke_table j on c.joke_id = j.id " +
            "WHERE j.id = :id " +
            "ORDER BY c.date_time_created DESC", nativeQuery = true)
    List<Comment> findAllNewestByJokeId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM comment_table c " +
            "INNER JOIN user_table u on c.user_id = u.id " +
            "WHERE u.id = :id " +
            "ORDER BY c.date_time_created DESC", nativeQuery = true)
    List<Comment> findAllNewestByUserId(@Param("id") Integer id);

    void deleteById(Integer id);
}

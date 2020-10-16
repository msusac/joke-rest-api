package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findOneByUsername(String username);

    User findOneByEmail(String email);
    User findOneByUsernameAndEnabledTrue(String username);

    @Query(value = "SELECT * FROM user_table u " +
            "WHERE UPPER(u.username) LIKE UPPER(CONCAT('%',:username,'%')) AND " +
            "UPPER(u.email) LIKE UPPER(CONCAT('%',:email,'%'))", nativeQuery = true)
    List<User> findAllByUsernameAndEmail(@Param("username") String username, @Param("email") String email);

    User save(User user);

    void deleteById(Integer id);
}

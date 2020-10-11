package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findOneByUsername(String username);

    User findOneByEmail(String email);
    User findOneByUsernameAndEnabledTrue(String username);

    User save(User user);

    void deleteById(Integer id);
}

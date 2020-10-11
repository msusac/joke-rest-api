package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    Authority findOneByName(String name);
}

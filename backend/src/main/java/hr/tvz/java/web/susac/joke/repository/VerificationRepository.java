package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.model.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Integer> {

    Verification save(Verification verification);

    Verification findOneByToken(String token);

    @Query(value = "SELECT * FROM verification_table v " +
            "WHERE v.token = :token " +
            "AND v.verified = FALSE " +
            "AND v.date_expiry > current_date", nativeQuery = true)
    Verification findOneByTokenNotExpired(@Param("token") String token);

    @Query(value = "SELECT * FROM verification_table v " +
            "WHERE v.verified = FALSE " +
            "AND (cast(current_date as date) - cast(v.date_expiry as date)) >= 1", nativeQuery = true)
    List<Verification> findAllByDateExpiredDayLater();

    void deleteById(Integer id);
}

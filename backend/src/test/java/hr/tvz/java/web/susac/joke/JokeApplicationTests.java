package hr.tvz.java.web.susac.joke;

import hr.tvz.java.web.susac.joke.configuration.SchedulerConfig;
import hr.tvz.java.web.susac.joke.jobs.VerificationJob;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@MockBean({SchedulerConfig.class, VerificationJob.class})
@TestPropertySource(locations = "classpath:application-test.properties")
class JokeApplicationTests {

	@Test
	void contextLoads() {
	}

}

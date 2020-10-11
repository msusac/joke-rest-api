package hr.tvz.java.web.susac.joke;

import hr.tvz.java.web.susac.joke.configuration.SchedulerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@MockBean(SchedulerConfig.class)
class JokeApplicationTests {

	@Test
	void contextLoads() {
	}

}

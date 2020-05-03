package funk.shane.tc;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class TCAppTests extends AbstractContainerDatabaseTest {
	private DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;

	@ClassRule
	public static MySQLContainer mysql = (MySQLContainer) new MySQLContainer("mysql:5.7.12")
			.withLogConsumer(new Slf4jLogConsumer(log));

	@Test
	void testContainerTry() throws Exception {

		mysql.start();

		try {
			ResultSet rs = query(mysql, "SELECT now()");
			String dbValue = rs.getString(1);

			String dateStr = dtf.format(LocalDate.now());
			assertThat(dbValue).startsWith(dateStr);
		}
		finally {
			mysql.stop();
		}

	}
}

package com.matthewcasperson;

import com.matthewcasperson.domain.MicroserviceKeyValue;
import com.matthewcasperson.repository.MicroserviceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Test suite that verifies the behaviour of the microservice. This test uses a H2
 * in memory database to simulate the datastore, providing a convenient way to test all
 * the JPA/Repository/Elide things without needing any external services.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("TEST")
public class MicroserviceTemplateApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(MicroserviceTemplateApplicationTests.class);

	/**
	 * We'll use these to populate the in memory H2 database
	 */
	private static final Map<String, String> TEST_DATA = new HashMap<String, String>() {{
		put("Key1", "Value1");
		put("Key2", "Value2");
		put("Key3", "Value3");
		put("Key4", "Value4");
		put("Key5", "Value5");
	}};

	/**
	 * The H2 database needs to have the correct tables created so JPA can work with it.
	 */
	private static final String CREATE_TABLE = "CREATE TABLE MicroserviceDatabase.Microservice(" +
			"id int auto_increment primary key, key varchar(45), value varchar(45))";

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private MicroserviceRepository microserviceRepository;

	@Autowired
	private EntityManagerFactory emf;

	/**
	 * Create and populate H2 tables
	 */
	@Before
	public void setup() throws Exception {
		EntityManager em = null;
		try {
			/*
				A native query that creates the tables
			 */
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.createNativeQuery(CREATE_TABLE).executeUpdate();
			em.getTransaction().commit();

			mockMvc = webAppContextSetup(webApplicationContext).build();

			/*
				Save some mock data
			 */
			for (final String key : TEST_DATA.keySet()) {
				final MicroserviceKeyValue appQuoteMicroserviceEntity = new MicroserviceKeyValue();
				appQuoteMicroserviceEntity.setValue(TEST_DATA.get(key));
				appQuoteMicroserviceEntity.setKey(key);
				microserviceRepository.save(appQuoteMicroserviceEntity);
			}
		} catch (final Exception ex) {
			LOGGER.error("Error preparing database", ex);
			if (em != null && em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Test
	public void elideFilterTest() throws Exception {
		final ResultActions resultActions = mockMvc.perform(
				get("/microservice/microserviceKeyValue?secret=dontHackMe"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data", hasSize(1)));
	}
}
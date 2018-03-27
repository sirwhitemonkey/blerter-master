package com.blerter.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blerter.constant.Status;
import com.blerter.factory.UtilsFactory;
import com.blerter.model.Response;
import com.blerter.persistence.Health;
import com.blerter.persistence.Safety;
import com.blerter.service.MasterService;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:*cucumber.xml")
public class HealthTestSteps {
	private final static Logger logger = LogManager.getLogger(HealthTestSteps.class);
	private transient UtilsFactory utilsFactory = UtilsFactory.factory();
	
	@Autowired
	private MasterService masterService;
	private long id;

	@Given("^Validate service$")
	public void validateService() throws Throwable {
		String prefix = "validateService() ";
		logger.info(prefix);
		assertEquals(true, masterService != null);
	}

	@And("^Clear$")
	public void clearData() throws Throwable {
		String prefix = "clearData() ";
		logger.info(prefix);
		Response.Builder response = masterService.delete();
		assertEquals(true, response.getResponseCode() == Status.Ok.value());
	}

	@Then("^Post health$")
	public void postHealth() throws Throwable {
		String prefix = "postHealth() ";
		logger.info(prefix);
		Health health = new Health();
		health.setName("health name");
		health.setDescription("health description");
		List<Safety> safety = new ArrayList<Safety>();
		for (int count = 0; count < 3; count++) {
			Safety sf = new Safety();
			sf.setName("Safety name:" + count);
			sf.setDescription("Safety description:" + count);
			sf.setIsActive((count % 2) == 0);
			sf.setHealth(health);
			safety.add(sf);
		}
		health.setSafety(safety);

		Response.Builder response = masterService.postHealth(health);
		assertEquals(true, response.getResponseCode() == Status.Ok.value());
	}

	@And("^Set health: (.+)$")
	public void setHealth(long id) throws Throwable {
		String prefix = "setHealth() ";
		logger.info(prefix);
		this.id = id;
		assertEquals(true, id >= 0);
	}

	@Then("^Unknown health$")
	public void getUnknownHealth() throws Throwable {
		String prefix = "getUnknownHealth() ";
		logger.info(prefix);
		Response.Builder response = masterService.getHealth(id);
		assertEquals(true, response.getResponseCode() != Status.Ok.value());
	}

	@Then("^Valid health$")
	public void getValidHealth() throws Throwable {
		String prefix = "getValidHealth() ";
		logger.info(prefix);
		if (id == 0) {
			Response.Builder response = masterService.getHealth();
			assertEquals(true, response.getResponseCode() == Status.Ok.value());
			List<com.blerter.model.Health> healths = response.getHealthList();
			assertEquals(true, healths != null);

			response = masterService.getHealth(healths.get(0).getId());
			assertEquals(true, response.getResponseCode() == Status.Ok.value());

		} else {
			assertEquals(true, false);
		}

	}

	@And("^Modify health name: (.+)$")
	public void modifyHealthName(String name) throws Throwable {
		String prefix = "modifyHealthName() ";
		logger.info(prefix + " name:" + name);
		if (id == 0) {
			Response.Builder response = masterService.getHealth();
			assertEquals(true, response.getResponseCode() == Status.Ok.value());
			List<com.blerter.model.Health> healths = response.getHealthList();
			assertEquals(true, healths != null);

			com.blerter.model.Health health = healths.get(0);
			id = health.getId();
			
			Health putHealth = new Health();
			putHealth.setId(health.getId());
			putHealth.setName(name);
			putHealth.setDescription(health.getDescription());
			
			if (putHealth.getSafety() != null) {
				List<Safety> safety = new ArrayList<Safety>();
				putHealth.getSafety().forEach(obj -> {
					Safety sf = new Safety();
					sf.setId(obj.getId());
					sf.setName(obj.getName());
					sf.setDescription(obj.getDescription());
					sf.setHealth(putHealth);
					safety.add(sf);
				});
				putHealth.setSafety(safety);
			}

			response = masterService.putHealth(putHealth);
			assertEquals(true, response.getResponseCode() == Status.Ok.value());

		} else {
			assertEquals(true, false);
		}

		assertEquals(true, id >= 0);
	}

	@Then("^Validate health name: (.+)$")
	public void validateHealthName(String name) throws Throwable {
		String prefix = "validateHealthName() ";
		logger.info(prefix);
		Response.Builder response = masterService.getHealth(id);
		assertEquals(true, response.getResponseCode() == Status.Ok.value().intValue());
		com.blerter.persistence.Health health = (com.blerter.persistence.Health) utilsFactory
				.deserialize(response.getData());
		assertEquals(true, health.getName().equalsIgnoreCase(name));
	}

	@Then("^Delete health$")
	public void deleteHealth() throws Throwable {
		String prefix = "deleteHealth() ";
		logger.info(prefix);
		if (id == 0) {
			Response.Builder response = masterService.getHealth();
			assertEquals(true, response.getResponseCode() == Status.Ok.value());
			List<com.blerter.model.Health> healths = response.getHealthList();
			assertEquals(true, healths != null);
			response = masterService.deleteHealth(healths.get(0).getId());
			assertEquals(true, response.getResponseCode() == Status.Ok.value());
		} else {
			assertEquals(true, false);
		}

	}
}


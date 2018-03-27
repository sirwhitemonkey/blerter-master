package com.blerter.service;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.blerter.constant.Status;
import com.blerter.factory.PersistenceManagerHandlerFactory;
import com.blerter.factory.UtilsFactory;
import com.blerter.model.Response;
import com.blerter.persistence.Health;
import com.blerter.persistence.Safety;
import com.blerter.repository.HealthRepository;
import com.blerter.repository.impl.HealthRepositoryImpl;

/**
 * Master service
 *
 */
@Service
public class MasterService {

	private static Logger logger = LogManager.getLogger(MasterService.class);
	private transient PersistenceManagerHandlerFactory persistenceManagerHandlerFactory = PersistenceManagerHandlerFactory
			.factory();
	private transient UtilsFactory utilsFactory = UtilsFactory.factory();

	/**
	 * Post health
	 */
	public Response.Builder postHealth(Health health) {
		String prefix = "postHealth() ";
		logger.info(prefix + health.toJson());
		Response.Builder responseBuilder = Response.newBuilder();
		PersistenceManagerFactory persistenceManagerFactory = null;
		PersistenceManager persistenceManager = null;

		try {
			persistenceManagerFactory = persistenceManagerHandlerFactory.getPersistenceManagerFactory();
			if (persistenceManagerFactory != null) {
				persistenceManager = persistenceManagerFactory.getPersistenceManager();
				HealthRepository healthRepository = new HealthRepositoryImpl(persistenceManager);

				// set parent-child relationship
				if (health.getSafety() != null) {
					health.getSafety().forEach(safety -> {
						safety.setHealth(health);
					});
				}

				if (healthRepository.save(health)) {
					responseBuilder.setResponseCode(Status.Ok.value());
					responseBuilder.setInfo("Health insert successful");

				} else {
					responseBuilder.setResponseCode(Status.BadRequest.value());
					responseBuilder.setInfo("Health insert failed");
				}

			} else {
				responseBuilder.setResponseCode(Status.BadRequest.value());
				responseBuilder.setInfo("Persistence manager factory is null");
			}

		} catch (Exception exc) {
			exc.printStackTrace();
			responseBuilder.setResponseCode(Status.InternalServerError.value());
			responseBuilder.setInfo(exc.getMessage());
		} finally {
			if (persistenceManager != null) {
				persistenceManager.close();
			}
		}
		return responseBuilder;
	}

	/**
	 * Put health
	 */
	public Response.Builder putHealth(Health health) {
		String prefix = "putHealth() ";
		logger.info(prefix + health.toJson());
		Response.Builder responseBuilder = Response.newBuilder();
		PersistenceManagerFactory persistenceManagerFactory = null;
		PersistenceManager persistenceManager = null;

		try {
			persistenceManagerFactory = persistenceManagerHandlerFactory.getPersistenceManagerFactory();
			if (persistenceManagerFactory != null) {
				persistenceManager = persistenceManagerFactory.getPersistenceManager();
				HealthRepository healthRepository = new HealthRepositoryImpl(persistenceManager);

				Health modifiedHealth = healthRepository.findById(health.getId());
				modifiedHealth.setName(health.getName());
				modifiedHealth.setDescription(health.getDescription());
				modifiedHealth.setSafety(new ArrayList<Safety>());  
				if (health.getSafety() != null) {
					health.getSafety().forEach(safety -> {
						safety.setHealth(modifiedHealth);
						modifiedHealth.getSafety().add(safety);
					});
				}
				if (healthRepository.save(modifiedHealth)) {
					responseBuilder.setResponseCode(Status.Ok.value());
					responseBuilder.setInfo("Health update successful");

				} else {
					responseBuilder.setResponseCode(Status.BadRequest.value());
					responseBuilder.setInfo("Health update failed");
				}

			} else {
				responseBuilder.setResponseCode(Status.BadRequest.value());
				responseBuilder.setInfo("Persistence manager factory is null");
			}

		} catch (Exception exc) {
			exc.printStackTrace();
			responseBuilder.setResponseCode(Status.InternalServerError.value());
			responseBuilder.setInfo(exc.getMessage());
		} finally {
			if (persistenceManager != null) {
				persistenceManager.close();
			}
		}
		return responseBuilder;
	}

	/**
	 * Delete health
	 */
	public Response.Builder deleteHealth(Long id) {
		String prefix = "deleteHealth() ";
		logger.info(prefix + "id :" + id);
		Response.Builder responseBuilder = Response.newBuilder();
		PersistenceManagerFactory persistenceManagerFactory = null;
		PersistenceManager persistenceManager = null;

		try {
			persistenceManagerFactory = persistenceManagerHandlerFactory.getPersistenceManagerFactory();
			if (persistenceManagerFactory != null) {
				persistenceManager = persistenceManagerFactory.getPersistenceManager();
				HealthRepository healthRepository = new HealthRepositoryImpl(persistenceManager);

				if (healthRepository.delete(id)) {
					responseBuilder.setResponseCode(Status.Ok.value());
					responseBuilder.setInfo("Health delete successful");

				} else {
					responseBuilder.setResponseCode(Status.BadRequest.value());
					responseBuilder.setInfo("Health delete failed");
				}

			} else {
				responseBuilder.setResponseCode(Status.BadRequest.value());
				responseBuilder.setInfo("Persistence manager factory is null");
			}

		} catch (Exception exc) {
			exc.printStackTrace();
			responseBuilder.setResponseCode(Status.InternalServerError.value());
			responseBuilder.setInfo(exc.getMessage());
		} finally {
			if (persistenceManager != null) {
				persistenceManager.close();
			}
		}
		return responseBuilder;
	}

	/**
	 * Delete health
	 */
	public Response.Builder delete() {
		String prefix = "delete() ";
		logger.info(prefix);
		Response.Builder responseBuilder = Response.newBuilder();
		PersistenceManagerFactory persistenceManagerFactory = null;
		PersistenceManager persistenceManager = null;

		try {
			persistenceManagerFactory = persistenceManagerHandlerFactory.getPersistenceManagerFactory();
			if (persistenceManagerFactory != null) {
				persistenceManager = persistenceManagerFactory.getPersistenceManager();
				HealthRepository healthRepository = new HealthRepositoryImpl(persistenceManager);

				if (healthRepository.delete()) {
					responseBuilder.setResponseCode(Status.Ok.value());
					responseBuilder.setInfo("Delete health(s) successful");
				} else {
					responseBuilder.setResponseCode(Status.BadRequest.value());
					responseBuilder.setInfo("Delete healths(s) failed");
				}

			} else {
				responseBuilder.setResponseCode(Status.BadRequest.value());
				responseBuilder.setInfo("Persistence manager factory is null");
			}

		} catch (Exception exc) {
			exc.printStackTrace();
			responseBuilder.setResponseCode(Status.InternalServerError.value());
			responseBuilder.setInfo(exc.getMessage());
		} finally {
			if (persistenceManager != null) {
				persistenceManager.close();
			}
		}
		return responseBuilder;
	}
	
	/**
	 * Get health
	 */
	public Response.Builder getHealth(Long id) {
		String prefix = "getHealth() ";
		logger.info(prefix + "id :" + id);
		Response.Builder responseBuilder = Response.newBuilder();
		PersistenceManagerFactory persistenceManagerFactory = null;
		PersistenceManager persistenceManager = null;

		try {
			persistenceManagerFactory = persistenceManagerHandlerFactory.getPersistenceManagerFactory();
			if (persistenceManagerFactory != null) {
				persistenceManager = persistenceManagerFactory.getPersistenceManager();
				HealthRepository healthRepository = new HealthRepositoryImpl(persistenceManager);

				Health health = healthRepository.findById(id);
				if (health != null) {
					responseBuilder.setResponseCode(Status.Ok.value());
					responseBuilder.setData(utilsFactory.serialize(health));
				} else {
					responseBuilder.setResponseCode(Status.BadRequest.value());
					responseBuilder.setInfo("Health query failed");
				}

			} else {
				responseBuilder.setResponseCode(Status.BadRequest.value());
				responseBuilder.setInfo("Persistence manager factory is null");
			}

		} catch (Exception exc) {
			exc.printStackTrace();
			responseBuilder.setResponseCode(Status.InternalServerError.value());
			responseBuilder.setInfo(exc.getMessage());
		} finally {
			if (persistenceManager != null) {
				persistenceManager.close();
			}
		}
		return responseBuilder;
	}
	
	/**
	 * Get health
	 */
	public Response.Builder getHealth() {
		String prefix = "getHealth() ";
		logger.info(prefix);
		
		Response.Builder responseBuilder = Response.newBuilder();
		PersistenceManagerFactory persistenceManagerFactory = null;
		PersistenceManager persistenceManager = null;

		try {
			persistenceManagerFactory = persistenceManagerHandlerFactory.getPersistenceManagerFactory();
			if (persistenceManagerFactory != null) {
				persistenceManager = persistenceManagerFactory.getPersistenceManager();
				HealthRepository healthRepository = new HealthRepositoryImpl(persistenceManager);

				List<Health> healths = healthRepository.findAll();
				if (healths != null &&  !healths.isEmpty()) {
					responseBuilder.setResponseCode(Status.Ok.value());
					
					healths.forEach(health -> {
						com.blerter.model.Health.Builder healthBuilder = com.blerter.model.Health.newBuilder();
						healthBuilder.setId(health.getId());
						healthBuilder.setName(health.getName());
						healthBuilder.setDescription(health.getDescription());
						if (health.getSafety() != null) {
							health.getSafety().forEach(safety -> {
								com.blerter.model.Safety.Builder safetyBuilder = com.blerter.model.Safety.newBuilder();
								safetyBuilder.setId(safety.getId());
								safetyBuilder.setName(safety.getName());
								safetyBuilder.setDescription(safety.getDescription());
								safetyBuilder.setIsActive(safety.getIsActive());
								healthBuilder.addSafety(safetyBuilder);
							});
						}
						responseBuilder.addHealth(healthBuilder);
					});
				} else {
					responseBuilder.setResponseCode(Status.BadRequest.value());
					responseBuilder.setInfo("Health query failed");
				}

			} else {
				responseBuilder.setResponseCode(Status.BadRequest.value());
				responseBuilder.setInfo("Persistence manager factory is null");
			}

		} catch (Exception exc) {
			exc.printStackTrace();
			responseBuilder.setResponseCode(Status.InternalServerError.value());
			responseBuilder.setInfo(exc.getMessage());
		} finally {
			if (persistenceManager != null) {
				persistenceManager.close();
			}
		}
		return responseBuilder;
	}
	
}


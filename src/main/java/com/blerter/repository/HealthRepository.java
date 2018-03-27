package com.blerter.repository;

import java.util.List;

import com.blerter.persistence.Health;

/**
 * Health repository
 *
 */
public interface HealthRepository {
	/**
	 * Get health
	 * @param id
	 */
	Health findById(Long id);
	
	/**
	 * Get health
	 */
	List<Health> findAll();
	
	/**
	 * Save health
	 * @param health
	 */
	boolean save(Health health);
	
	/**
	 * Delete health
	 * @param id
	 */
	boolean delete(Long id);
	
	/**
	 * Delete health
	 */
	boolean delete();
}

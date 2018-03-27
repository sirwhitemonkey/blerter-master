package com.blerter.repository.impl;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.blerter.persistence.Health;
import com.blerter.persistence.QHealth;
import com.blerter.repository.BaseRepository;
import com.blerter.repository.HealthRepository;
import com.querydsl.jdo.JDOQuery;

public class HealthRepositoryImpl extends BaseRepository implements HealthRepository {

	public HealthRepositoryImpl(PersistenceManager persistenceManager) {
		super(persistenceManager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blerter.repository.HealthRepository#findById(java.lang.Long)
	 */
	@SuppressWarnings("resource")
	@Override
	public Health findById(Long id) {
		QHealth qHealth = QHealth.health;
		JDOQuery<?> query = new JDOQuery<Void>(getPersistenceManager());
		Health health = query.select(qHealth).from(qHealth).where(qHealth.id.eq(id.longValue())).fetchOne();
		//query.close();

		return health;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blerter.repository.HealthRepository#findAll()
	 */
	@SuppressWarnings("resource")
	@Override
	public List<Health> findAll() {
		QHealth qHealth = QHealth.health;
		JDOQuery<?> query = new JDOQuery<Void>(getPersistenceManager());
		List<Health> healths = query.select(qHealth).from(qHealth).fetch();
		//query.close();

		return healths;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.blerter.repository.HealthRepository#save(com.blerter.persistence.Health)
	 */
	@Override
	public boolean save(Health health) {
		boolean isSave = false;
		Transaction transaction = getPersistenceManager().currentTransaction();
		try {
			transaction.setOptimistic(true);
			transaction.begin();
			getPersistenceManager().makePersistent(health);

			isSave = true;
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (isSave) {
				transaction.commit();
			} else {
				transaction.rollback();
			}
		}
		return isSave;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blerter.repository.HealthRepository#delete(java.lang.Long)
	 */
	@Override
	public boolean delete(Long id) {
		boolean isDelete = false;
		Transaction transaction = getPersistenceManager().currentTransaction();
		try {
			transaction.begin();
			Health health = findById(id);
			if (health != null) {
				getPersistenceManager().deletePersistent(health);
				isDelete = true;
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (isDelete) {
				transaction.commit();
			} else {
				transaction.rollback();
			}
		}
		return isDelete;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blerter.repository.HealthRepository#delete()
	 */
	@Override
	public boolean delete() {
		boolean isDelete = false;
		Transaction transaction = getPersistenceManager().currentTransaction();
		try {
			transaction.begin();
			List<Health> healths = findAll();
			getPersistenceManager().deletePersistentAll(healths);
			isDelete = true;
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (isDelete) {
				transaction.commit();
			} else {
				transaction.rollback();
			}
		}
		return isDelete;
	}

}

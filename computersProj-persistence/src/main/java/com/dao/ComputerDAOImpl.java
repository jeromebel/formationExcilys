package com.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.om.Computer;
import com.servlet.wrapper.PageWrapper;

@Repository("computerDAO")
public class ComputerDAOImpl implements ComputerDAO {
	final Logger log = LoggerFactory.getLogger(ComputerDAOImpl.class);

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@Override
	public List<Computer> readByPage(PageWrapper page) throws SQLException {

		int offset = (page.getPageNumber() - 1) * page.getComputerPerPage();

		String orderBy = "ORDER BY " + page.getOrderedBy() + " " + page.getOrderDirection() + " ";
		@SuppressWarnings("unchecked")
		List<Computer> computers = entityManager.createQuery("SELECT c FROM Computer as c LEFT JOIN c.company f WITH f.name LIKE :nameSearch WHERE c.name LIKE :nameSearch " + orderBy)
		.setParameter("nameSearch", "%" + page.getFilterName() + "%")
		.setFirstResult(offset)
		.setMaxResults(page.getComputerPerPage())
		.getResultList();
		
		long numberTotalOfComputers = (long) entityManager.createQuery("SELECT count(computer) FROM Computer as computer LEFT JOIN computer.company comp WITH comp.name LIKE :nameSearch WHERE computer.name LIKE :nameSearch")
		.setParameter("nameSearch", "%" + page.getFilterName() + "%")
		.getSingleResult();
		page.setTotalNumberOfRecords((int) numberTotalOfComputers);
		log.info("readPage: elements totals add to the wrapper");
		
		return computers;
	}

	@Override
	public Computer readFilterByID(Long id) throws SQLException {

		Computer c = entityManager.find(Computer.class, id);

		return c;
	}

	@Override
	public void delete(String id) throws NumberFormatException, SQLException {

		Computer c = new Computer();
		c.setId(Long.parseLong(id));
		try {
			this.entityManager.remove(this.entityManager.merge(c));
			log.debug("Computer num " + id + " well deleted");
		} catch (Exception e) {
			log.error("problem to delete Computer id : " + c.getId());
			log.error(e.getMessage());
		}
	}

	@Override
	public void update(Computer c) throws SQLException {

		try {
			entityManager.merge(c);
			log.info("Computer num " + c.getId() + " well Update");
		} catch (Exception e) {
			log.error("problem to update Computer id : " + c.getId());
			log.error(e.getMessage());
		}

	}

	@Override
	public void create(Computer c) throws SQLException {

		this.entityManager.persist(c);

		log.info("Computer num " + c.getId() + " well created");

	}

}

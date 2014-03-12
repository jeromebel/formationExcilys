package com.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
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

	private Criteria getCriteria() {
		Session s = (Session) entityManager.getDelegate();
		Criteria crit = s.createCriteria(Computer.class);
		return crit;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Computer> readByPage(PageWrapper page) throws SQLException {

		int offset = (page.getPageNumber() - 1) * page.getComputerPerPage();

		List<Computer> computers;

		Criteria crit = getCriteria();
		crit.add(Restrictions.like("name", "%" + page.getFilterName() + "%"))
				.createAlias("company", "f",JoinType.LEFT_OUTER_JOIN)
				.setMaxResults(page.getComputerPerPage())
				.setFirstResult(offset);

		String orderField = page.getOrderedBy();
		if (orderField.startsWith("c."))
			orderField = orderField.substring(2);

		log.debug("orderField : " + orderField);

		if ("DESC".equals(page.getOrderDirection()))
			crit.addOrder(Order.desc(orderField));
		else
			crit.addOrder(Order.asc(orderField));

		computers = crit.list();

		long numberTotalOfComputers = (long) entityManager
				.createQuery(
						"SELECT count(computer) FROM Computer as computer LEFT JOIN computer.company comp WITH comp.name LIKE :nameSearch WHERE computer.name LIKE :nameSearch")
				.setParameter("nameSearch", "%" + page.getFilterName() + "%")
				.getSingleResult();
		page.setTotalNumberOfRecords((int) numberTotalOfComputers);
		log.info("readPage: elements totals add to the wrapper");

		return computers;
	}

	@Override
	public Computer readFilterByID(Long id) throws SQLException {

		@SuppressWarnings("unchecked")
		List<Computer> computers = getCriteria().list();

		if ((computers == null) || (computers.size() > 1))
			log.error("Problem to get computer by id");

		// return entityManager.find(Computer.class, id);
		return computers.get(0);
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

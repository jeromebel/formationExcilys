package com.ws;

import java.util.List;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import com.dto.MapComputer;
import com.om.Computer;
import com.om.Log;
import com.repository.ComputerRepo;
import com.repository.LogRepo;
import com.servlet.wrapper.PageWrapper;

@WebService(endpointInterface = "com.ws.ComputerService")
public class ComputerServiceImpl implements ComputerService {

	final Logger LOG = LoggerFactory.getLogger(ComputerServiceImpl.class);

	@Autowired
	private ComputerRepo cRepo;

	@Autowired
	private LogRepo logDB;

	@Override
	@Transactional(readOnly = true)
	public void readByPage(PageWrapper page) {

		String orderField = page.getOrderBy();
		if (orderField == null)
			orderField = "c.id";
		if (orderField.startsWith("c."))
			orderField = orderField.substring(2);
		if (orderField.startsWith("f."))
			orderField = orderField.replaceAll("f.", "company.");

		Direction sortDirection;
		if ("DESC".equals(page.getOrderDirection()))
			sortDirection = Sort.Direction.DESC;
		else
			sortDirection = Sort.Direction.ASC;

		PageRequest request = new PageRequest(page.getPageNumber()-1,
				page.getComputerPerPage(), sortDirection, orderField);

		Page<Computer> computersPage;
		if (!"".equals(page.getFilterName()))
			computersPage = cRepo.findByNameLike(request, "%"+page.getFilterName()+"%");
		else
			computersPage = cRepo.findAll(request);

		page.setNumberOfPages(computersPage.getTotalPages());

		page.setTotalNumberOfRecords((int) computersPage.getTotalElements());

		List<Computer> computers = computersPage.getContent();
		page.setResults(MapComputer.getComputersDTO(computers));

	}

	@Override
	@Transactional(readOnly = true)
	public Computer readFilterByID(Long id) {

		Computer result;
		result = cRepo.findById(id);
		result.setId(id);

		return result;
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(String id) {
		Computer c = new Computer();
		c = cRepo.findById(Long.valueOf(id));
		cRepo.delete(c);
		// cRepo.deleteById(Long.valueOf(id));
		Log log = new Log();
		log.setComputer(c);
		log.setDescription("Computer "+c.getName()+" deleted");
		logDB.save(log);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Computer c) {
		cRepo.save(c);
		Log log = new Log();
		log.setComputer(c);
		log.setDescription("Computer "+c.getName()+" updated");
		logDB.save(log);
	}

	@Override
	@Transactional(readOnly = false)
	public void create(Computer c) {
		cRepo.save(c);
		Log log = new Log();
		log.setComputer(c);
		log.setDescription("Computer "+c.getName()+" created");
		logDB.save(log);

	}

}

package model.dao.impl;

import java.util.List;

import db.DB;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.repository.DepartmentDaoRepository;

public class DepartmentDaoJDBC implements DepartmentDao{

	DepartmentDaoRepository departmentDaoRepository = new DepartmentDaoRepository(DB.getConnection());
	
	@Override
	public void insert(Department department) {
		departmentDaoRepository.insert(department);		
	}

	@Override
	public void update(Department department) {
		departmentDaoRepository.update(department);
		
	}

	@Override
	public void deleteById(Integer id) {
		departmentDaoRepository.deleteById(id);
		
	}

	@Override
	public Department findById(Integer id) {
		return departmentDaoRepository.findById(id);
	}

	@Override
	public List<Department> findAll() {
		return departmentDaoRepository.findAll();
	}

}

package model.dao.impl;

import java.util.List;

import db.DB;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import model.repository.SellerDaoRepository;

public class SellerDaoJDBC implements SellerDao {
	
	SellerDaoRepository sellerDaoRepository = new SellerDaoRepository(DB.getConnection());

	@Override
	public void insert(Seller seller) {
		sellerDaoRepository.insert(seller);
		
	}

	@Override
	public void update(Seller seller) {
		sellerDaoRepository.update(seller);
		
	}

	@Override
	public void deleteById(Integer id) {
		sellerDaoRepository.deleteById(id);
		
	}

	@Override
	public Seller findById(Integer id) {
		return sellerDaoRepository.findById(id);
	}

	@Override
	public List<Seller> findAll() {
		return sellerDaoRepository.findAll();
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		return sellerDaoRepository.findByDepartment(department);
	}
	
	

}

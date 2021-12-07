package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoRepository {
	
	private Connection conn = null; 
	DepartmentDaoRepository departmentDaoRepository = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	
	public SellerDaoRepository(Connection conn) {
		this.conn = conn;
		departmentDaoRepository = new DepartmentDaoRepository(this.conn);
	}

	public List<Seller> findAll(){
		try {
			//pst = conn.prepareStatement("SELECT SL.*, DP.NAME AS DEPNAME "
			//						+ "FROM SELLER SL INNER JOIN DEPARTMENT DP "
			//						+ "ON SL.DEPARTMENT_ID = DP.ID");
			
			pst = conn.prepareStatement("SELECT * FROM SELLER");
			rs = pst.executeQuery();
			List<Seller> list = new ArrayList<>(); 
			
			while(rs.next()) {
				Seller seller = instantiateSeller1(rs);
				list.add(seller);
				
			}
			return list;
		}
		catch(SQLException error) {
			throw new DbException(error.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
		}
	}
	
	public Seller findById(Integer id) {
		try {
			pst = conn.prepareStatement("SELECT SELLER.*, DEPARTMENT.NAME AS DEPNAME"
									+ " FROM SELLER INNER JOIN DEPARTMENT"
									+ " ON SELLER.DEPARTMENT_ID = DEPARTMENT.ID"		// Aqui nós pegamos TAMBÉM as informações do nome dos departamentos,
									+ " WHERE SELLER.ID = ?");							// logo, não será necessário pesquisar por id no bd de Department.
			pst.setInt(1, id);
			rs = pst.executeQuery(); 
			
			if(rs.next()) {
				Seller seller = instantiateSeller2(rs);
				return seller;
			}
			
			return null;
			
		}
		catch(SQLException error) {
			throw new DbException(error.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
		}
	}
	
	public void insert(Seller seller) {
		try {
			PreparedStatement pst = conn.prepareStatement("INSERT INTO SELLER VALUES "
															+ "(NULL, ?, ?, ?, ?, ?)");
			pst.setString(1, seller.getName());
			pst.setString(2, seller.getEmail());
			pst.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			pst.setInt(4, seller.getBaseSalary());
			pst.setInt(5, seller.getDepartment().getId());
			pst.execute();
		}
		catch(SQLException error) {
			throw new DbException(error.getMessage());
		}
		finally {
			DB.closeStatement(pst);
		}
	}
	
	public void update(Seller seller) {
		try {
			PreparedStatement pst = conn.prepareStatement("UPDATE SELLER "
														+ "SET NAME = ?, EMAIL = ?, BIRTHDATE = ?, BASESALARY = ?, DEPARTMENT_ID = ? "
														+ "WHERE ID = ?");
			pst.setString(1, seller.getName());
			pst.setString(2, seller.getEmail());
			pst.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			pst.setInt(4, seller.getBaseSalary());
			pst.setInt(5, seller.getDepartment().getId());
			pst.setInt(6, seller.getId());
			
			pst.execute();
		}
		catch(SQLException error) {
			throw new DbException(error.getMessage());
		}
		finally {
			DB.closeStatement(pst);
		}
	}
	
	public void deleteById(Integer id) {
		try {
			PreparedStatement pst = conn.prepareStatement("DELETE FROM SELLER "
														+ "WHERE ID = ?");
			
			pst.setInt(1, id);
			pst.execute();
		}
		catch(SQLException error) {
			throw new DbException(error.getMessage());
		}
		finally {
			DB.closeStatement(pst);
		}
	}
	
	public List<Seller> findByDepartment(Department department) {
		try {
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM SELLER WHERE DEPARTMENT_ID = ?");
			pst.setInt(1, department.getId());
			ResultSet rs = pst.executeQuery();
			List<Seller> list = new ArrayList<>();
			
			while(rs.next()) {
				Seller seller = instantiateSeller1(rs);
				list.add(seller);
			}
			return list;
		}
		catch(SQLException error) {
			throw new DbException(error.getMessage());
		}
		finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}
	
	public Seller instantiateSeller1(ResultSet rs) {
		try {
			Seller seller = new Seller();
			seller.setId(rs.getInt("ID"));
			seller.setName(rs.getString("NAME"));
			seller.setEmail(rs.getString("EMAIL"));						
			seller.setBirthDate(rs.getDate("BIRTHDATE"));										
			seller.setBaseSalary(rs.getInt("BASESALARY"));
			seller.setDepartment(departmentDaoRepository.findById(rs.getInt("DEPARTMENT_ID")));	// <-- Nessa instanciação, usei o método findById do Department
			return seller;																		// para retornar os dados que eu precisava. Dessa forma, não é
		}																						// necessário trazer as informações do departamento no comando SQL.
		catch(SQLException error) {
			throw new DbException(error.getMessage());
		}
	}
	
	public Seller instantiateSeller2(ResultSet rs) {
		try {
			Seller seller = new Seller();
			seller.setId(rs.getInt("ID"));
			seller.setName(rs.getString("NAME"));
			seller.setEmail(rs.getString("EMAIL"));
			seller.setBirthDate(rs.getDate("BIRTHDATE"));
			seller.setBaseSalary(rs.getInt("BASESALARY"));
			seller.setDepartment(new Department(rs.getInt("DEPARTMENT_ID"), rs.getString("DEPNAME")));	// <-- Nessa instanciaçã, usei as informações que
			return seller;																				// foram retornadas pelo comando SQL, não precisando
		}																								// usar os métodos do repositório Department. Ambos as
		catch(SQLException error) {																		// formas são válidas, o programador que define a melhor.
			throw new DbException(error.getMessage());
		}
	}
}

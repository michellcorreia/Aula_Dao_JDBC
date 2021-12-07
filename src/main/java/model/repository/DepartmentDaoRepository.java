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

public class DepartmentDaoRepository {
	
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null; 
	
	public DepartmentDaoRepository(Connection conn) {
		this.conn = conn;
	}
	
	public List<Department> findAll(){
		try {
			pst = conn.prepareStatement("SELECT * FROM DEPARTMENT");
			rs = pst.executeQuery();
			List<Department> list = new ArrayList<>(); 
			
			while(rs.next()) {
				list.add(instantiateDepartment(rs));
			}
			return list;
		}
		catch(SQLException error) {
			throw new DbException(error.getMessage());
		}
		finally{
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}
	
	
	public Department findById(Integer id){
		try {
			pst = conn.prepareStatement("SELECT * FROM DEPARTMENT WHERE ID = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery(); 
			
			if(rs.next()) {
				Department dep = instantiateDepartment(rs);
				return dep;
			}
			return null;
		}
		catch(SQLException error) {
			throw new DbException(error.getMessage());
		}
		finally{
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
		}
	}
	
	public void insert(Department department) {
		try {
			PreparedStatement pst = conn.prepareStatement("INSERT INTO DEPARTMENT VALUES "
															+ "(NULL, ?)");
			pst.setString(1, department.getName());
			pst.execute();
		}
		catch(SQLException error) {
			throw new DbException(error.getMessage());
		}
		finally {
			DB.closeStatement(pst);
		}
	}
	
	public void update(Department department) {
		try {
			PreparedStatement pst = conn.prepareStatement("UPDATE DEPARTMENT SET NAME = ? WHERE ID = ?");
			pst.setString(1, department.getName());
			pst.setInt(2, department.getId());
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
			PreparedStatement pst = conn.prepareStatement("DELETE FROM DEPARTMENT WHERE ID = ?");
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
	
	public Department instantiateDepartment(ResultSet rs) {
		try {
			Department dep = new Department();
			dep.setId(rs.getInt("ID"));
			dep.setName(rs.getString("NAME"));
			return dep;
		}
		catch(SQLException error) {
			throw new DbException(error.getMessage());
		}
	}
}

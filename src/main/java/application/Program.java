package application;

import java.util.Date;

import db.DB;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String args[]) {
		
		DepartmentDao dd = new DepartmentDaoJDBC();
		SellerDao sd = new SellerDaoJDBC();
		
		Seller seller = new Seller(4, "NewTeste", "NewTeste@gmail.com", new Date(), 2000, dd.findById(3));
		
		Department department = new Department(2, "TESTE");
		
		for(Department dep : dd.findAll()) {
			System.out.println(dep);
		}
		for(Seller sel : sd.findAll()) {
			System.out.println(sel);
		}
	}
}

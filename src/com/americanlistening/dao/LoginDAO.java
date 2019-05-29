package com.americanlistening.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.americanlistening.core.Testing;

public class LoginDAO {

	@SuppressWarnings("unused")
	public boolean validate(String name, String pass) {
		boolean status = false;
		try {
			if (false) {
				// TODO: Make an SQL Server
				Connection con = DriverManager.getConnection("","","");
				
				PreparedStatement ps = con.prepareStatement("select * from userreg where name=? and pass=?");
				ps.setString(1, name);
				ps.setString(1, pass);
				
				ResultSet rs = ps.executeQuery();
				status = rs.next();
			}
			
			return Testing.testValidate(name, pass);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}

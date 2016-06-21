package gov.va.vba.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DataDAO {
	private Connection connection;
	
	public DataDAO() throws Exception {
		connection = DBUtility.getConnection();
	}
	
	public ArrayList<String> getFrameWork(String frameWork) {
		ArrayList<String> list = new ArrayList<String>();
		PreparedStatement ps = null;
		String data;
		try {
			ps = connection.prepareStatement("SELECT");
			ps.setString(1, frameWork + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				data = rs.getString("FRAMEWORK");
				list.add(data);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
}
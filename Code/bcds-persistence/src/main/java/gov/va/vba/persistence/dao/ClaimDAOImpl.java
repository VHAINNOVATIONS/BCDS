package main.java.gov.va.vba.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.ResultSet;
//import java.sql.SQLException;
import java.sql.Statement;
//import java.sql.PreparedStatement;

import main.java.gov.va.vba.persistence.database.DBConnectionFactory;
import main.java.gov.va.vba.persistence.database.DBUtil;
//import org.springframework.dao.DataAccessException;

public class ClaimDAOImpl implements ClaimDAO {

	private Connection dbConnection;
	private Statement statement;
	
	private String SQL_SELECT = "SELECT VET_ID, VET_NM, \"REGN_OFfC\", CLAIM_ID, DATE_OF_CLAIM, CEST_DATE, CNTNTN_CLMANT_TXT FROM MV_STGN_CLAIM";

	ArrayList<Claim> claims = new ArrayList<Claim>();
	Claim claim = null;
	
	public ClaimDAOImpl() {
		try {
			dbConnection = DBConnectionFactory.getConnection();
			statement = dbConnection.createStatement();
			ResultSet rs = statement.executeQuery(SQL_SELECT);

			while (rs.next()) {
				claim.setVetId(rs.getInt("VET_ID"));
				claim.setVetName(rs.getString("VET_NM"));
				claim.setOffice(rs.getString("REGN_OFfC"));
				claim.setClaimId(rs.getInt("CLAIM_ID"));
				claim.setClaimDate(rs.getDate("DATE_OF_CLAIM"));
				claim.setCESTDate(rs.getDate("CES_DATE"));
				claim.setContentions(rs.getString("CNTNTN_CLMANT_TXT"));
				
				claims.add(claim);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Override
	public void deleteClaim(Claim claim) {
		claims.remove(claim.getClaimId());
		System.out.print("Claim ID: " + claim.getClaimId() + " deleted from database");
	}
	
	@Override
	public List<Claim> getAllClaims() {
		return claims;
	}
	
	@Override
	public Claim getClaim(int id) {
		return claims.get(id);
	}
	
	@Override
	public void updateClaim(Claim claim) {
		claims.get(claim.getClaimId()).setVetName(claim.getVetName());
		claims.get(claim.getClaimId()).setVetId(claim.getVetId());
		claims.get(claim.getClaimId()).setOffice(claim.getOffice());
		claims.get(claim.getClaimId()).setModel(claim.getModel());
		claims.get(claim.getClaimId()).setContentions(claim.getContentions());
		claims.get(claim.getClaimId()).setClaimDate(claim.getClaimDate());
		claims.get(claim.getClaimId()).setCESTDate(claim.getCESTDate());
		claims.get(claim.getClaimId()).setLastModDate(claim.getLastModDate());
		//System.out.pring("Claim ID: " + claim.getClaimId() + " updated!!!");
	}
}
package gov.va.vba.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import gov.va.vba.persistence.servlet.JDBCUtility;
import gov.va.vba.persistence.dao.Claim;
//import org.springframework.dao.DataAccessException;

public class ClaimDAOImpl implements ClaimDAO {

	private Connection dbConnection;
	private PreparedStatement pStmt;
	
	private String SQL_SELECT = "SELECT VET_ID, VET_NM, REGN_OFfC, CLAIM_ID, DATE_OF_CLAIM, CEST_DATE, CNTNTN_CLMANT_TXT FROM MV_STGN_CLAIM";

	ArrayList<Claim> claims = new ArrayList<Claim>();
	Claim claim = null;
	
	public ClaimDAOImpl() {
		try {
			dbConnection = JDBCUtility.getConnection();
			pStmt = dbConnection.prepareStatement(SQL_SELECT);
			ResultSet rs = pStmt.executeQuery();

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
		//System.out.pring("Claim ID: " + claim.getClaimId() + " deleted from database");
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
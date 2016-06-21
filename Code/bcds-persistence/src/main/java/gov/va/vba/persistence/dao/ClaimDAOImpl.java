package gov.va.vba.persistence.dao;

import java.util.ArrayList;
import java.util.List;
//import org.springframework.dao.DataAccessException;

public class ClaimDAOImpl implements ClaimDAO {

	List<Claim> claims;
	
	public ClaimDAOImpl() {
		claims = new ArrayList<Claim>();
		//Claim claim1 = new Claim();
		//Claim claim2 = new Claim();
		//claims.add(claim1);
		//claims.add(claim2);
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
		claims.get(claim.getClaimId()).setModel(claim.getOffice());
		claims.get(claim.getClaimId()).setClaimDate(claim.getClaimDate());
		claims.get(claim.getClaimId()).setCESTDate(claim.getCESTDate());
		claims.get(claim.getClaimId()).setLastModDate(claim.getLastModDate());
		//System.out.pring("Claim ID: " + claim.getClaimId() + " updated!!!");
	}
}
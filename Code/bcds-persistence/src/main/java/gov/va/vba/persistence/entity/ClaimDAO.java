package gov.va.vba.persistence.entity;

import java.util.List;

public interface ClaimDAO {
	public List<Claim> getAllClaims();
	public Claim getClaim(int vetId);
	public void updateClaim(Claim claim);
	public void deleteClaim(Claim claim);
}
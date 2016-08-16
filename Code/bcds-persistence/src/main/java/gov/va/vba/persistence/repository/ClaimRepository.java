package gov.va.vba.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.va.vba.persistence.entity.Claim;

/**
 * Spring Data JPA repository for the Claim entity.
 */
public interface ClaimRepository extends JpaRepository<Claim, Long> {

	@Query(value = "SELECT PTCPNT_VET_ID, CLAIM_RO_NAME, BNFT_CLAIM_ID, DATE_OF_CLAIM, CNTNTN_CLMANT_TXT FROM BCDSS.AH4929_RATING_CORP_CLAIM WHERE CNTNTN_CLMANT_TXT LIKE '%KNEE%' OR CNTNTN_CLMANT_TXT LIKE '%EAR%'", nativeQuery = true)
	public List<Claim> findAll();

}

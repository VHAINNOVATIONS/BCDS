package gov.va.vba.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.va.vba.persistence.entity.Claim;

/**
 * Spring Data JPA repository for the Claim entity.
 */
public interface ClaimRepository extends JpaRepository<Claim, Long> {

	@Query(value = "SELECT * FROM ah4929_rating_corp_claim JOIN ah4929_person on ah4929_rating_corp_claim.ptcpnt_vet_id = ah4929_person.ptcpnt_vet_id WHERE cntntn_clsfcn_id in (230,270,3690,3700,3710,8919,3720,3730,3780,3790,3800)", nativeQuery = true)
	public List<Claim> findAll();
	
	@Query(value = "SELECT BNFT_CLAIM_ID, DATE_OF_CLAIM, END_PRDCT_TYPE_CD,  AH4929_PERSON.PTCPNT_VET_ID, AH4929_PERSON.DOB, CLAIM_RO_NUMBER, CNTNTN_CLSFCN_ID FROM ah4929_rating_corp_claim JOIN ah4929_person on ah4929_rating_corp_claim.ptcpnt_vet_id = ah4929_person.ptcpnt_vet_id WHERE cntntn_clsfcn_id in (230,270,3690,3700,3710,8919,3720,3730,3780,3790,3800)", nativeQuery = true)
	public List<Claim> selectedFields();

	@Query(value = "SELECT PTCPNT_VET_ID, CLAIM_RO_NAME, BNFT_CLAIM_ID, DATE_OF_CLAIM, CNTNTN_CLMANT_TXT FROM BCDSS.AH4929_RATING_CORP_CLAIM WHERE CNTNTN_CLMANT_TXT LIKE '%KNEE%' OR CNTNTN_CLMANT_TXT LIKE '%EAR%'", nativeQuery = true)
	public List<Claim> oldFindAll();
	
	@Query(value = "SELECT PTCPNT_VET_ID, CLAIM_RO_NAME, BNFT_CLAIM_ID, DATE_OF_CLAIM, CNTNTN_CLMANT_TXT FROM BCDSS.AH4929_RATING_CORP_CLAIM WHERE (CNTNTN_CLMANT_TXT LIKE '%KNEE%' OR CNTNTN_CLMANT_TXT LIKE '%EAR%') AND ROWNUM <= 20 ORDER BY PTCPNT_VET_ID", nativeQuery = true)
	public List<Claim> findFirstNumberedRow();

}

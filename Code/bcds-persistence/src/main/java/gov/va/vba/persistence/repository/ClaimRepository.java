package gov.va.vba.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.va.vba.persistence.entity.Claim;

/**
 * Spring Data JPA repository for the Claim entity.
 */
public interface ClaimRepository extends JpaRepository<Claim, Long> {

	@Query(value = "SELECT PTCPNT_VET_ID, PRFIL_DT, BNFT_CLAIM_ID, END_PRDCT_TYPE_CD, DATE_OF_CLAIM, PAYEE_TYPE_CD, BNFT_CLAIM_TYPE_CD, CLAIM_LABEL, STATUS_TYPE_CD, CLAIM_RO_NUMBER, CLAIM_RO_NAME, CNTNTN_ID, CNTNTN_CLSFCN_ID, CNTNTN_TYPE_CD, CNTNTN_CLMANT_TXT, CNTNTN_MED_IND, CNTNTN_WELL_GRNDED_APLCBL_IND, CNTNTN_BEGIN_DT, CNTNTN_SPECL_ISSUE_ID, CNTNTN_SPECL_ISSUE_TYPE_CD FROM BCDSS.AH4929_RATING_CORP_CLAIM WHERE CNTNTN_CLMANT_TXT LIKE '%KNEE%' OR CNTNTN_CLMANT_TXT LIKE '%EAR%'", nativeQuery = true)
	public List<Claim> findAll();

	@Query(value = "SELECT PTCPNT_VET_ID, PRFIL_DT, BNFT_CLAIM_ID, END_PRDCT_TYPE_CD, DATE_OF_CLAIM, PAYEE_TYPE_CD, BNFT_CLAIM_TYPE_CD, CLAIM_LABEL, STATUS_TYPE_CD, CLAIM_RO_NUMBER, CLAIM_RO_NAME, CNTNTN_ID, CNTNTN_CLSFCN_ID, CNTNTN_TYPE_CD, CNTNTN_CLMANT_TXT, CNTNTN_MED_IND, CNTNTN_WELL_GRNDED_APLCBL_IND, CNTNTN_BEGIN_DT, CNTNTN_SPECL_ISSUE_ID, CNTNTN_SPECL_ISSUE_TYPE_CD  FROM AH4929_RATING_CORP_CLAIM WHERE CNTNTN_CLSFCN_ID in (230,270,3690,3700,3710,8919,3720,3730,3780,3790,3800)", nativeQuery = true)
	public List<Claim> selectedClaims();

	@Query(value = "SELECT PTCPNT_VET_ID, PRFIL_DT, BNFT_CLAIM_ID, END_PRDCT_TYPE_CD, DATE_OF_CLAIM, PAYEE_TYPE_CD, BNFT_CLAIM_TYPE_CD, CLAIM_LABEL, STATUS_TYPE_CD, CLAIM_RO_NUMBER, CLAIM_RO_NAME, CNTNTN_ID, CNTNTN_CLSFCN_ID, CNTNTN_TYPE_CD, CNTNTN_CLMANT_TXT, CNTNTN_MED_IND, CNTNTN_WELL_GRNDED_APLCBL_IND, CNTNTN_BEGIN_DT, CNTNTN_SPECL_ISSUE_ID, CNTNTN_SPECL_ISSUE_TYPE_CD FROM BCDSS.AH4929_RATING_CORP_CLAIM WHERE (CNTNTN_CLMANT_TXT LIKE '%KNEE%' OR CNTNTN_CLMANT_TXT LIKE '%EAR%') AND ROWNUM <= 20 ORDER BY PTCPNT_VET_ID", nativeQuery = true)
	public List<Claim> findFirstNumberedRow();

	@Query(value = "SELECT c FROM Claim c WHERE c.veteran.veteranId = ?1 AND c.contentionClsfcnId IN (?2) AND c.profileDate >= c.claimDate")
	List<Claim> findByVeteranVeteranIdAndContentionClsfcnIdIn(Long veteranId, List<Long> ids);

	List<Claim> findByClaimDateBetween(Date fromDate, Date toDate);

	@Query(value = "SELECT c FROM Claim c WHERE (?1 is null or ?1='' or c.contentionClaimTextKeyForModel LIKE CONCAT('%',?1,'%')) AND (?2 is null or ?2='' or c.regionalOfficeOfClaim LIKE CONCAT('%',?2,'%')) AND (c.claimDate >= ?3 AND c.claimDate <= ?4)")
	List<Claim> findClaimSByRangeOnClaimDate(String contentionType, String regionalOffice, Date fromDate, Date toDate);

	@Query(value = "SELECT c FROM Claim c WHERE (?1 is null or ?1='' or c.contentionClaimTextKeyForModel LIKE CONCAT('%',?1,'%')) AND (?2 is null or ?2='' or c.regionalOfficeOfClaim LIKE CONCAT('%',?2,'%')) AND (c.profileDate >= ?3 AND c.profileDate <= ?4)")
	List<Claim> findClaimSByRangeOnProfileDate(String contentionType, String regionalOffice, Date fromDate, Date toDate);
}
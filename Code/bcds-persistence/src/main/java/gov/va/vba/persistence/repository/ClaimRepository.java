package gov.va.vba.persistence.repository;

import gov.va.vba.persistence.constants.QueryConstants;
import gov.va.vba.persistence.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Spring Data JPA repository for the Claim entity.
 */
public interface ClaimRepository extends JpaRepository<Claim, Long> {

	@Query(value = "SELECT PTCPNT_VET_ID, BNFT_CLAIM_ID, END_PRDCT_TYPE_CD, DATE_OF_CLAIM, (DATE_OF_CLAIM + '10') as CEST_DATE, PAYEE_TYPE_CD, BNFT_CLAIM_TYPE_CD, CLAIM_LABEL, STATUS_TYPE_CD, CLAIM_RO_NUMBER, CLAIM_RO_NAME, CNTNTN_ID, CNTNTN_CLSFCN_ID, CNTNTN_TYPE_CD, CNTNTN_CLMANT_TXT, CNTNTN_MED_IND, CNTNTN_WELL_GRNDED_APLCBL_IND, CNTNTN_BEGIN_DT FROM BCDSS.AH4929_RATING_CORP_CLAIM WHERE CNTNTN_CLMANT_TXT LIKE '%KNEE%' OR CNTNTN_CLMANT_TXT LIKE '%EAR%'", nativeQuery = true)
	public List<Claim> findAll();

	@Query(value = "SELECT distinct C.PTCPNT_VET_ID, BNFT_CLAIM_ID, END_PRDCT_TYPE_CD, DATE_OF_CLAIM, (DATE_OF_CLAIM + '10') as CEST_DATE, PAYEE_TYPE_CD, BNFT_CLAIM_TYPE_CD, CLAIM_LABEL, STATUS_TYPE_CD, CLAIM_RO_NUMBER, CLAIM_RO_NAME, CNTNTN_ID, CNTNTN_CLSFCN_ID, CNTNTN_TYPE_CD, CNTNTN_CLMANT_TXT, CNTNTN_MED_IND, CNTNTN_WELL_GRNDED_APLCBL_IND, CNTNTN_BEGIN_DT FROM BCDSS.AH4929_RATING_CORP_CLAIM C , BCDSS.AH4929_PERSON P, BCDSS.AH4929_RATING_DECISION D WHERE C.PTCPNT_VET_ID = P.PTCPNT_VET_ID AND  C.PTCPNT_VET_ID = D.PTCPNT_VET_ID AND D.PRCNT_NBR is not null and CNTNTN_CLSFCN_ID in (230,270,3690,3700,3710,8919,3720,3730,3780,3790,3800)", nativeQuery = true)
	public List<Claim> selectedClaims();

	@Query(value= "SELECT Distinct C.PTCPNT_VET_ID, BNFT_CLAIM_ID, END_PRDCT_TYPE_CD, DATE_OF_CLAIM, PAYEE_TYPE_CD, BNFT_CLAIM_TYPE_CD, CLAIM_LABEL, STATUS_TYPE_CD, CLAIM_RO_NUMBER, CLAIM_RO_NAME,CNTNTN_ID, CNTNTN_CLSFCN_ID, CNTNTN_TYPE_CD, CNTNTN_CLMANT_TXT, CNTNTN_MED_IND, CNTNTN_WELL_GRNDED_APLCBL_IND, CNTNTN_BEGIN_DT FROM BCDSS.AH4929_RATING_CORP_CLAIM C , BCDSS.AH4929_PERSON P WHERE C.PTCPNT_VET_ID = P.PTCPNT_VET_ID and CNTNTN_CLSFCN_ID in ('230','270','2200','2210','3140','3150','3690','3700','3710','3720','3730','3780','3790','3800','4130','4210','4700','4920','5000','5010','5710','6850','8919') AND END_PRDCT_TYPE_CD like '02%' AND ROWNUM <= 150 ORDER BY C.PTCPNT_VET_ID, DATE_OF_CLAIM", nativeQuery = true)
	public List<Claim> findFirstNumberedRow();

	/*@Query(value = "SELECT c FROM Claim c WHERE c.veteran.veteranId = ?1 AND c.contentionClsfcnId IN (?2) AND c.profileDate >= c.claimDate")
	List<Claim> findByVeteranVeteranIdAndContentionClsfcnIdIn(Long veteranId, List<Long> ids);*/

	List<Claim> findByClaimDateBetween(Date fromDate, Date toDate);

	@Query(value = "SELECT c FROM Claim c WHERE (?1 is null or ?1='' or c.contentionClaimTextKeyForModel LIKE CONCAT('%',?1,'%')) AND (?2 is null or c.claimRegionalOfficeNumber = ?2) AND (c.claimDate >= ?3 AND c.claimDate <= ?4)")
	List<Claim> findClaimSByRangeOnClaimDate(String contentionType, Long regionalOfficeNumber, Date fromDate, Date toDate);

	/*@Query(value = "SELECT c FROM Claim c WHERE (?1 is null or ?1='' or c.contentionClaimTextKeyForModel LIKE CONCAT('%',?1,'%')) AND (?2 is null or c.claimRegionalOfficeNumber = ?2) AND (c.profileDate >= ?3 AND c.profileDate <= ?4)")
	List<Claim> findClaimSByRangeOnProfileDate(String contentionType, Long regionalOfficeNumber, Date fromDate, Date toDate);*/

	@Query(value = QueryConstants.CONTENTIONS_COUNT)
	List<Object[]> aggregateContentions(Long claimId, Long veteranId);

	Claim findOneByClaimIdAndContentionClsfcnIdIn(Long claimId, List<Long> cntntClsfIds);
	
}
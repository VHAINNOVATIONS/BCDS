package gov.va.vba.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.va.vba.persistence.entity.Veteran;

/**
 * Spring Data JPA repository for the veteran entity.
 */
public interface VeteranRepository extends JpaRepository<Veteran, Long> {

	@Query(value = "SELECT PTCPNT_VET_ID, DOB FROM BCDSS.AH4929_PERSON WHERE CNTNTN_CLMANT_TXT LIKE '%KNEE%' OR CNTNTN_CLMANT_TXT LIKE '%EAR%'", nativeQuery = true)
	public List<Veteran> findAll();
	
	@Query(value = "SELECT PTCPNT_VET_ID, DOB FROM BCDSS.AH4929_PERSON WHERE (CNTNTN_CLMANT_TXT LIKE '%KNEE%' OR CNTNTN_CLMANT_TXT LIKE '%EAR%') AND ROWNUM <= 20 ORDER BY PTCPNT_VET_ID", nativeQuery = true)
	public List<Veteran> findFirstNumberedRow();

}

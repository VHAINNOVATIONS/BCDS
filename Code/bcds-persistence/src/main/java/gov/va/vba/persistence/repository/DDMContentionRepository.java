package main.java.gov.va.vba.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.va.vba.persistence.entity.DDMContention;

/**
 * Spring Data JPA repository for the DDM Contention entity.
 */
public interface DDMContentionRepository extends JpaRepository<DDMContention, Long> {

	@Query(value = "SELECT CNTNT_ID, CTLG_ID, CNTNT_CD, MODEL_TYPE, CNTNT_CD_DESC, CRTD_BY, CRTD_DTM FROM DDM_CNTNT", nativeQuery = true)
	public List<DDMContention> DDMContention();
	
	List<DDMContention> findByContentionId(Long contentionId);
}

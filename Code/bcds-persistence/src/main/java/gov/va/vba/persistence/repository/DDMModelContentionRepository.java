package gov.va.vba.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.va.vba.persistence.entity.DDMModelContention;

/**
 * Spring Data JPA repository for the DDM Model Contention entity.
 */
public interface DDMModelContentionRepository extends JpaRepository<DDMModelContention, Long> {

	@Query(value = "SELECT PATTERN_ID, CNTNT_ID, COUNT, MODEL_TYPE FROM DDM_MODEL_PATTERN", nativeQuery = true)
	public List<DDMModelContention> modelContention();
}

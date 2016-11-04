package main.java.gov.va.vba.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.va.vba.persistence.entity.DDMCategory;

/**
 * Spring Data JPA repository for the DDM Category entity.
 */
public interface DDMCategoryRepository extends JpaRepository<DDMCategory, Long> {

	@Query(value = "SELECT CTLG_ID, CTLG_DESC FROM DDM_CTLG", nativeQuery = true)
	public List<DDMCategory> DDMCategory();
}

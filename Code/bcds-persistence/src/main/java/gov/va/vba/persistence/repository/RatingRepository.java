package gov.va.vba.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.va.vba.persistence.entity.Rating;

/**
 * Spring Data JPA repository for the Claim entity.
 */
public interface RatingRepository extends JpaRepository<Rating, Long> {

	@Query(value = "SELECT ptcpnt_vet_id, diagnosis_code, prcnt_nbr FROM ah4929_rating_decision WHERE diagnosis_code in (5055,5161,5162,5163,5164,5165,5256,5257,5258,5259,5260,5261,5262,5263,5264,5313,5314,5315) AND prcnt_nbr IS NOT NULL", nativeQuery = true)
	public List<Rating> eligibility();
	}

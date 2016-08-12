package gov.va.vba.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.va.vba.persistence.entity.Claim;

/**
 * Spring Data JPA repository for the Claim entity.
 */
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    

}

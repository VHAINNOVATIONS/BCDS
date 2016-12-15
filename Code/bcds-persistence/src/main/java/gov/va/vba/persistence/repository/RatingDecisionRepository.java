package gov.va.vba.persistence.repository;

import gov.va.vba.persistence.constants.QueryConstants;
import gov.va.vba.persistence.entity.RatingDecision;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by ProSphere User on 11/6/2016.
 */
@Repository
public interface RatingDecisionRepository extends CrudRepository<RatingDecision, BigDecimal>{

    //@Query(value = "SELECT r FROM RatingDecision r WHERE veteranId = ?1")
    List<RatingDecision> findByVeteranVeteranIdAndProfileDateLessThanAndPercentNumberNotNullAndDiagnosisCodeIn(Long veteranId, Date claimDate, List<String> diagnosisCodes);

    @Query(value = "SELECT c.profileDate FROM RatingDecision c WHERE c.veteran.veteranId = ?1 AND ?2 > (SELECT MAX(e.profileDate) FROM RatingDecision e)")
    List<Date> findPreviousClaims(Long veteranId, Date claimDate);

    @Query(value = "SELECT c.profileDate FROM RatingDecision c WHERE c.veteran.veteranId = ?1 AND ?2 <= (SELECT MAX(e.profileDate) FROM RatingDecision e)")
    List<Date> findCurrentClaims(Long veteranId, Date claimDate);

    @Query(value = QueryConstants.DECISION_DETAILS, nativeQuery = true)
    List<Object[]> findDecisionDetails(Long veteranId);

    @Query(value = QueryConstants.CALCULATE_CDD)
    List<Integer> calculateDecisionCDD(Long veteranId, Date profileDate);

    List<RatingDecision> findByVeteranVeteranIdAndPercentNumberNotNullAndDiagnosisCodeIn(Long veteranId, List<String> kneeDiagnosisCodes);

}

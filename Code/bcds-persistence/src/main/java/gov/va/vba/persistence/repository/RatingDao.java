package gov.va.vba.persistence.repository;

import gov.va.vba.persistence.models.data.DecisionDetails;
import gov.va.vba.persistence.models.data.DiagnosisCount;
import gov.va.vba.persistence.models.data.KneeClaim;

import java.util.Date;
import java.util.List;

/**
 * Created by ProSphere User on 12/16/2016.
 */
public interface RatingDao {

    List<DiagnosisCount> getDiagnosisCount(long veteranId, Date claimDate);

    List<KneeClaim> getPreviousClaims(long veteranId, long claimId);

    List<DecisionDetails> getDecisionsPercentByClaimDate(long veteranId, Date claimDate);
}

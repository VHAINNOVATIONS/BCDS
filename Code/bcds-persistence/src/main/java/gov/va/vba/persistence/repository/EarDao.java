package gov.va.vba.persistence.repository;

import gov.va.vba.persistence.models.data.ClaimDetails;
import gov.va.vba.persistence.models.data.DecisionDetails;

import java.util.Date;
import java.util.List;

/**
 * Created by ProSphere User on 1/4/2017.
 */
public interface EarDao {


    List<ClaimDetails> getClaims(long veteranId, long claimId);

    List<ClaimDetails> getPreviousClaims(long veteranId, Date claimDate);

    List<DecisionDetails> getDecisionsPercentByClaimDate(long veteranId, Date claimDate);
}

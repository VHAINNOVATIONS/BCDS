package gov.va.vba.persistence.repository;

import gov.va.vba.persistence.entity.DDMModelPatternIndex;
import gov.va.vba.persistence.models.data.ContentionDetails;
import gov.va.vba.persistence.models.data.DecisionDetails;
import gov.va.vba.persistence.models.data.DiagnosisCount;
import gov.va.vba.persistence.models.data.ClaimDetails;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ProSphere User on 12/16/2016.
 */
public interface RatingDao {

    List<DDMModelPatternIndex> getPatternAccuracy(Long patternId);

    List<DiagnosisCount> getDiagnosisCount(long veteranId, Date claimDate);

    List<ClaimDetails> getPreviousClaims(long veteranId, long claimId);

    List<DecisionDetails> getDecisionsPercentByClaimDate(long veteranId, Date claimDate);

    List<Long> getKneeCntntPattrens(Map<Long, Integer> contentionCount, List<Long> pattrens);

    List<Long> getKneeDiagPattrens(List<DiagnosisCount> diagCount, List<Long> pattrens);

    int getClaimaintAge(long veteranId, long claimId);
    
    List<Long> getProcessIDSeq();

    List<ClaimDetails> getClaims();

    List<ClaimDetails> getClaims(long veteranId, long claimId);

    ContentionDetails getContention(long contentionCode);
}

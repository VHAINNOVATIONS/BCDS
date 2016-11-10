package gov.va.vba.service.data;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;
import gov.va.vba.domain.Claim;
import gov.va.vba.persistence.entity.RatingDecision;
import gov.va.vba.persistence.repository.ClaimRepository;
import gov.va.vba.persistence.repository.RatingDecisionRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimDataService extends AbsDataService<gov.va.vba.persistence.entity.Claim, Claim> {

    @Autowired
    ClaimRepository claimRepository;

    @Autowired
    private RatingDecisionRepository ratingDecisionRepository;

    public ClaimDataService() {
        this.setClasses(gov.va.vba.persistence.entity.Claim.class, Claim.class);
    }

    public List<Claim> findAll() {
        List<Claim> output = new ArrayList<>();
        List<gov.va.vba.persistence.entity.Claim> input = ((ClaimRepository) repository).findAll();
        mapper.mapAsCollection(input, output, outputClass);
        return output;
    }

    public List<Claim> findFirstNumberedRow() {
        List<Claim> output = new ArrayList<>();
        List<gov.va.vba.persistence.entity.Claim> input = ((ClaimRepository) repository).findFirstNumberedRow();
        mapper.mapAsCollection(input, output, outputClass);
        return output;
    }

    public List<Claim> findByVeteranId(Long veteranId) {
        Long[] x = {230L,270L,3690L,3700L,3710L,8919L,3720L,3730L,3780L,3790L,3800L};
        String[] y = {"5055","5161","5162","5163","5164","5165","5256","5257","5258","5259","5260","5261","5262","5263","5264","5313","5314","5315"};
        List<Long> ids = Arrays.asList(x);
        List<gov.va.vba.persistence.entity.Claim> result = claimRepository.findByVeteranVeteranIdAndContentionClsfcnIdIn(veteranId, ids);
        List<gov.va.vba.persistence.entity.Claim> validClaims = result.stream().filter(c -> {
            List<RatingDecision> decisions = ratingDecisionRepository.findByVeteranIdAndProfileDateLessThanAndPercentNumberNotNullAndDiagnosisCodeIn(new BigDecimal(c.getVeteran().getVeteranId()), c.getClaimDate(), Arrays.asList(y));
            return CollectionUtils.isNotEmpty(decisions);
        }).collect(Collectors.toList());
        List<Claim> output = new ArrayList<>();
        mapper.mapAsCollection(validClaims, output, outputClass);
        return output;
    }

    public List<Claim> findClaimsInRanged(Date from, Date to) {
        List<gov.va.vba.persistence.entity.Claim> result = claimRepository.findByClaimDateBetween(from, to);
        List<Claim> output = new ArrayList<>();
        mapper.mapAsCollection(result, output, outputClass);
        return output;
    }

    public List<Claim> findClaims(boolean establishedDate, Date fromDate, Date toDate, String contentionType, String regionalOffice) {
        if(fromDate == null) {
            Calendar instance = Calendar.getInstance();
            instance.set(1900, 12, 31);
            fromDate = instance.getTime();
        }
        if(toDate == null) {
            toDate = new Date();
        }
        List<gov.va.vba.persistence.entity.Claim> result = (establishedDate) ? claimRepository.findClaimSByRangeOnProfileDate(contentionType, regionalOffice, fromDate, toDate) : claimRepository.findClaimSByRangeOnClaimDate(contentionType, regionalOffice, fromDate, toDate);
        List<Claim> output = new ArrayList<>();
        mapper.mapAsCollection(result, output, outputClass);
        return output;
    }
}

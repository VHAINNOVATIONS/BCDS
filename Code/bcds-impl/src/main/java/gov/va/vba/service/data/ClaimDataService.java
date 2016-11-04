package gov.va.vba.service.data;

import gov.va.vba.domain.Claim;
import gov.va.vba.persistence.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ClaimDataService extends AbsDataService<gov.va.vba.persistence.entity.Claim, Claim> {

    @Autowired
    ClaimRepository claimRepository;

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
        List<gov.va.vba.persistence.entity.Claim> result = claimRepository.findByVeteranVeteranId(veteranId);
        List<Claim> output = new ArrayList<>();
        mapper.mapAsCollection(result, output, outputClass);
        return output;
    }

    public List<Claim> findClaimsInRanged(Date from, Date to) {
        List<gov.va.vba.persistence.entity.Claim> result = claimRepository.findByClaimDateBetween(from, to);
        List<Claim> output = new ArrayList<>();
        mapper.mapAsCollection(result, output, outputClass);
        return output;
    }

    public List<Claim> findClaims(boolean establishedDate, Date fromDate, Date toDate, String contentionType, String regionalOffice) {
        List<gov.va.vba.persistence.entity.Claim> result = (establishedDate) ? claimRepository.findClaimSByRangeOnProfileDate(contentionType, regionalOffice, fromDate, toDate) : claimRepository.findClaimSByRangeOnClaimDate(contentionType, regionalOffice, fromDate, toDate);
        List<Claim> output = new ArrayList<>();
        mapper.mapAsCollection(result, output, outputClass);
        return output;
    }
}

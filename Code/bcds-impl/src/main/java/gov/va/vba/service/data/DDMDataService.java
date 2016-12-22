package gov.va.vba.service.data;

import gov.va.vba.persistence.entity.DDMModelPattern;
import gov.va.vba.persistence.repository.DDMModelPatternRepository;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DDMDataService extends AbsDataService<gov.va.vba.persistence.entity.DDMModelPattern, DDMModelPattern> {

    private static final Logger LOG = LoggerFactory.getLogger(DDMDataService.class);

    @Autowired
    private DDMModelPatternRepository ddmModelPatternRepository;

    public List<DDMModelPattern> getPatternId(String modelType, Long claimantAge, Long claimCount, Long contentionCount, Long priorCDD, Long CDDAge) {
        List<gov.va.vba.persistence.entity.DDMModelPattern> result = ddmModelPatternRepository.findPatternId(modelType, claimantAge, claimCount, contentionCount, priorCDD, CDDAge);
        List<DDMModelPattern> output = new ArrayList<>();
        mapper.mapAsCollection(result, output, outputClass);
        return output;
    }

    public List<DDMModelPattern> getPatternId(String modelType, Long claimantAge, Long claimCount, Long contentionCount, Long CDDAge) {
        List<gov.va.vba.persistence.entity.DDMModelPattern> result = ddmModelPatternRepository.findPatternId(modelType, claimantAge, claimCount, contentionCount, 0L, CDDAge);
        List<DDMModelPattern> output = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(result)) {
            mapper.mapAsCollection(result, output, DDMModelPattern.class);
            LOG.info("PATTERN FOUND {}", result);
        }
        return output;
    }

}

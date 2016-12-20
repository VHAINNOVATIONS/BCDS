package gov.va.vba.service.data;

import gov.va.vba.persistence.entity.DDMModelContention;
import gov.va.vba.persistence.repository.DDMModelContentionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DDMModelCntntService {

    private static final Logger LOG = LoggerFactory.getLogger(DDMModelCntntService.class);

    @Autowired
    private DDMModelContentionRepository ddmModelContentionRepository;

    public List<DDMModelContention> getPatternId(String modelType, Long contentionId, Long contentionCount) {
        List<DDMModelContention> result = ddmModelContentionRepository.findByContentionIdAndCountAndModelType(contentionId, contentionCount, modelType);
        LOG.info("PATTERN FOUND {}", result.size());
        return result;
    }

}

package gov.va.vba.service.data;

import gov.va.vba.persistence.entity.DDMModelContention;
import gov.va.vba.persistence.repository.DDMModelContentionRepository;
import gov.va.vba.persistence.repository.RatingDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DDMModelCntntService {

    private static final Logger LOG = LoggerFactory.getLogger(DDMModelCntntService.class);

    @Autowired
    private DDMModelContentionRepository ddmModelContentionRepository;

    @Autowired
    private RatingDao ratingDao;

    public List<DDMModelContention> getPatternId(String modelType, Long contentionId, Long contentionCount) {
        List<DDMModelContention> result = ddmModelContentionRepository.findByContentionIdAndCountAndModelType(contentionId, contentionCount, modelType);
        LOG.info("PATTERN FOUND {}", result.size());
        return result;
    }

    public List<Long> getKneePatternId(Map<Long, Integer> contentionCount, List<Long> pattrens) {
        List<Long> result = ratingDao.getKneeCntntPattrens(contentionCount, pattrens);
        LOG.info("PATTERN FOUND {}", result.size());
        return result;
    }

}

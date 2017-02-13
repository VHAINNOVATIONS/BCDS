package gov.va.vba.service.data;

import gov.va.vba.persistence.models.data.DiagnosisCount;
import gov.va.vba.persistence.repository.RatingDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DDMModelDiagService {

    private static final Logger LOG = LoggerFactory.getLogger(DDMModelDiagService.class);

    @Autowired
    private RatingDao ratingDao;

    public List<Long> getKneePatternId(List<DiagnosisCount> diagCount, List<Long> pattrens, String modelType) {
        List<Long> result = ratingDao.getKneeDiagPattrens(diagCount, pattrens, modelType);
        LOG.info("PATTERN FOUND {}", result.size());
        return result;
    }

}

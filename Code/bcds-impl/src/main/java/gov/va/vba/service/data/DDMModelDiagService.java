package gov.va.vba.service.data;

import gov.va.vba.persistence.entity.DDMModelContention;
import gov.va.vba.persistence.models.data.DiagnosisCount;
import gov.va.vba.persistence.repository.DDMModelContentionRepository;
import gov.va.vba.persistence.repository.RatingDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DDMModelDiagService {

    private static final Logger LOG = LoggerFactory.getLogger(DDMModelDiagService.class);

    @Autowired
    private RatingDao ratingDao;

    public List<Long> getKneePatternId(List<DiagnosisCount> contentionCount, List<Long> pattrens) {
        List<Long> result = ratingDao.getKneeDiagPattrens(contentionCount, pattrens);
        LOG.info("PATTERN FOUND {}", result.size());
        return result;
    }

}

package gov.va.vba.service.orika;

import gov.va.vba.domain.ModelRatingResults;
import gov.va.vba.domain.util.Veteran;
import gov.va.vba.domain.util.ModelPatternIndex;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by user on 11/16/2016.
 */
@Component
public class ModelRatingResultsMapper {

    private MapperFacade mapperFacade;

    public ModelRatingResultsMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        
        mapperFactory.classMap(gov.va.vba.persistence.entity.Veteran.class, Veteran.class)
                .field("veteranId", "veteranId")
                .register();
        mapperFactory.classMap(gov.va.vba.persistence.entity.DDMModelPatternIndex.class, ModelPatternIndex.class)
        		.field("patternId", "patternId")
        		.register();
        mapperFactory.classMap(gov.va.vba.persistence.entity.ModelRatingResults.class, ModelRatingResults.class)
		        .field("processId", "processId")
		        .field("veteran.veteranId", "veteran.veteranId")
		        .field("patternIndex.patternId", "patternIndex.patternId")
		        .field("patternIndex.accuracy", "patternIndex.accuracy")
		        .field("patternIndex.patternIndexNumber", "patternIndex.patternIndexNumber")
		        .field("patternIndex.CDD", "patternIndex.CDD")
		        .field("claimId", "claimId")
		        .field("claimDate", "claimDate")
		        .field("claimAge", "claimAge")
		        .field("modelType", "modelType")
		        .field("priorCDD", "priorCDD")
		        .field("quantPriorCDD", "quantPriorCDD")
		        .field("currentCDD", "currentCDD")
		        .field("quantCDD", "quantCDD")
		        .field("processDate", "processDate")
		        .byDefault()
                .register();
        
        mapperFacade = mapperFactory.getMapperFacade();
    }

    /**
     * Description: This method converts ModelRatingResults entity collection to model
     *
     * @param sourceEntity
     */
    public List<ModelRatingResults> mapCollection(List<gov.va.vba.persistence.entity.ModelRatingResults> sourceEntity) {
        List<ModelRatingResults> results = mapperFacade.mapAsList(sourceEntity, ModelRatingResults.class);
        return results;
    }
}

package gov.va.vba.service.orika;

import gov.va.vba.domain.ModelRatingResults;
import gov.va.vba.domain.ModelRatingResultsStatus;
import gov.va.vba.domain.util.Veteran;
import gov.va.vba.domain.util.ModelPatternIndex;
import gov.va.vba.domain.ModelRatingPattern;
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
		        .field("patternIndex.modelType", "patternIndex.modelType")
		        .field("claimId", "claimId")
		        .field("claimDate", "claimDate")
		        .field("claimAge", "claimAge")
		        .field("modelType", "modelType")
		        .field("priorCDD", "priorCDD")
		        .field("quantPriorCDD", "quantPriorCDD")
		        .field("currentCDD", "currentCDD")
		        .field("quantCDD", "quantCDD")
		        .field("processDate", "processDate")
		        .field("CDDAge", "CDDAge")
		        .field("claimCount", "claimCount")
		        .field("claim.contentionClaimTextKeyForModel", "claim.contentionClaimTextKeyForModel")
		        .byDefault()
                .register();
        mapperFactory.classMap(gov.va.vba.persistence.entity.ModelRatingResultsStatus.class, ModelRatingResultsStatus.class)
		        .field("id.processId", "processId")
		        .field("id.processStatus", "processStatus")
		        .field("crtdBy", "createdBy")
				.register();
        mapperFactory.classMap(gov.va.vba.persistence.entity.DDMModelPatternIndex.class, ModelRatingPattern.class)
		        .field("patternId", "patternIndex.patternId")
		        .field("accuracy", "patternIndex.accuracy")
		        .field("patternIndexNumber", "patternIndex.patternIndexNumber")
		        .field("CDD", "patternIndex.CDD")
		        .field("modelType", "patternIndex.modelType")
		        .field("categoryId", "categoryId")
		        .field("createdDate", "createdDate")
		        .field("createdBy", "createdBy")
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
    
    /**
     * Description: This method converts ModelRatingResultsStatusId entity collection to model
     *
     * @param sourceEntity
     */
    public List<ModelRatingResultsStatus> mapResultStatusCollection(List<gov.va.vba.persistence.entity.ModelRatingResultsStatus> sourceEntity) {
        List<ModelRatingResultsStatus> resultsStatus = mapperFacade.mapAsList(sourceEntity, ModelRatingResultsStatus.class);
        return resultsStatus;
    }
    
    /**
     * Description: This method converts ModelRatingPatternIndex entity collection to model
     *
     * @param sourceEntity
     */
    public List<ModelRatingPattern> mapModelRatingPatternCollection(List<gov.va.vba.persistence.entity.DDMModelPatternIndex> sourceEntity) {
        List<ModelRatingPattern> patternInfo = mapperFacade.mapAsList(sourceEntity, ModelRatingPattern.class);
        return patternInfo;
    }
}

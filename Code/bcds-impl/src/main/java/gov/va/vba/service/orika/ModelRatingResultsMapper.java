package gov.va.vba.service.orika;

import gov.va.vba.domain.ModelRatingResults;
import gov.va.vba.domain.util.Veteran;
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
        mapperFactory.classMap(gov.va.vba.persistence.entity.ModelRatingResults.class, ModelRatingResults.class)
		        .field("processId", "processId")
		        .field("patternId", "patternId")
		        .field("veteranId", "veteranId")
		        .field("processDate", "processDate")
		        .field("claimantAge", "claimantAge")
		        .field("dateOfBirth", "dateOfBirth")
		        .field("claimId", "claimId")
		        .field("endProductCode", "endProductCode")
		        .field("regionalOfficeNumber", "regionalOfficeNumber")
		        .field("claimDate", "claimDate")
		        .field("profileDate", "profileDate")
		        .field("promulgationDate", "promulgationDate")
		        .field("recentDate", "recentDate")
		        .field("modelType", "modelType")
		        .field("modelContentionCount", "modelContentionCount")
		        .field("contentionCount", "contentionCount")
		        //.field("priorCdd", "priorCdd")
		        //.field("quantPriorCdd", "quantPriorCdd")
		        //.field("currentCdd", "currentCdd")
		        //.field("quantCdd", "quantCdd")
		        .field("claimAge", "claimAge")
		        .field("CDDAge", "CDDAge")
		        .field("claimCount", "claimCount")
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

package gov.va.vba.service.orika;

import gov.va.vba.domain.ModelRatingResultsDiag;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class ModelRatingResultsDiagMapper {
	
	private MapperFacade mapperFacade;

    public ModelRatingResultsDiagMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        
        mapperFactory.classMap(gov.va.vba.persistence.entity.ModelRatingResultsDiag.class, ModelRatingResultsDiag.class)
		        .field("id.diagId", "diagId")
		        .field("modelRatingResults.processId", "processId")
		        .field("count", "count")
		        .register();
        
        mapperFacade = mapperFactory.getMapperFacade();
    }
    
    /**
     * Description: This method converts ModelRatingResultsDiag entity collection to model
     *
     * @param sourceEntity
     */
    public List<ModelRatingResultsDiag> mapCollection(List<gov.va.vba.persistence.entity.ModelRatingResultsDiag> sourceEntity) {
        List<ModelRatingResultsDiag> results = mapperFacade.mapAsList(sourceEntity, ModelRatingResultsDiag.class);
        return results;
    }

}

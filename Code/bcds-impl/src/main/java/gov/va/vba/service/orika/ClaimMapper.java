package gov.va.vba.service.orika;

import gov.va.vba.domain.Claim;
import gov.va.vba.domain.util.Veteran;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ProSphere User on 11/16/2016.
 */
@Component
public class ClaimMapper {

    private MapperFacade mapperFacade;

    public ClaimMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(gov.va.vba.persistence.entity.Veteran.class, Veteran.class)
                .field("veteranId", "veteranId")
                .register();
        mapperFactory.classMap(gov.va.vba.persistence.entity.Claim.class, Claim.class)
                .field("claimDate", "claimDate")
                .field("cestDate", "cestDate")
                .field("claimId", "claimId")
                .field("veteran.veteranId", "veteran.veteranId")
                .field("regionalOfficeOfClaim", "regionalOfficeOfClaim")
                .field("contentionClaimTextKeyForModel", "contentionClaimTextKeyForModel")
                .register();
        mapperFacade = mapperFactory.getMapperFacade();

    }

    /**
     * Description: This method converts Claim entity collection to model
     *
     * @param sourceEntity
     */
    public List<Claim> mapCollection(List<gov.va.vba.persistence.entity.Claim> sourceEntity) {
        List<Claim> output = new ArrayList<>();
        mapperFacade.mapAsCollection(sourceEntity, output, Claim.class);
        return output;
    }
}

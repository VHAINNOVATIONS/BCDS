package gov.va.vba.service;

import gov.va.vba.service.orika.AnnotationClassMapBuilder;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * Created by Pete Grazaitis on 1/6/2016.
 * **/
 
@Component
public class MapperFacadeFactory implements FactoryBean<MapperFacade> {
    public MapperFacade getObject() throws Exception {

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
                .classMapBuilderFactory(new AnnotationClassMapBuilder.Factory())
                .build();
        mapperFactory.getConverterFactory().registerConverter(new PassThroughConverter(org.joda.time.DateTime.class));

//        mapperFactory.registerClassMap(
//                mapperFactory.classMap(ComplexCriterion.class, gov.va.vba.persistence.entity.ComplexCriterion.class)
//                        .field("criteria", "simpleCriteria")
//                        .byDefault()
//                        .toClassMap()
//        );

        return mapperFactory.getMapperFacade();
    }

    public Class<?> getObjectType() {
        return MapperFacade.class;
    }

    public boolean isSingleton() {
        return true;
    }
}


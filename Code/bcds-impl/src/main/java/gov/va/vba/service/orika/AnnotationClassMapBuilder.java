package gov.va.vba.service.orika;

import ma.glasnost.orika.DefaultFieldMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.metadata.*;
import ma.glasnost.orika.property.PropertyResolverStrategy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.Map;

public class AnnotationClassMapBuilder<A, B> extends ClassMapBuilder<A, B> {
    protected AnnotationClassMapBuilder(Type<A> aType, Type<B> bType, MapperFactory mapperFactory,  PropertyResolverStrategy propertyResolver,
                                        DefaultFieldMapper[] defaults) {
        super(aType, bType, mapperFactory, propertyResolver, defaults);
    }

    @Override
    public ClassMapBuilder<A, B> byDefault(DefaultFieldMapper... withDefaults) {

        Type<?> type = getAType();
        Map<String, Property> propertiesForA = getPropertyExpressions(getAType());
        boolean mappedIsA = true;

        Mapped mapped = type.getRawType().getAnnotation(Mapped.class);
        if (mapped == null) {
            type = getBType();
            mapped = getBType().getRawType().getAnnotation(Mapped.class);
            mappedIsA = false;
        }

        if (mapped != null) {
            for (Field field : type.getRawType().getDeclaredFields()) {

                Skip skip = field.getAnnotation(Skip.class);
                if (skip != null) {
                    fieldMap(field.getName()).exclude().add();
                    continue;
                }

                Copy copy = field.getAnnotation(Copy.class);
                if (copy != null) {
                    FieldMapBuilder<A, B> fieldMap = mappedIsA ? fieldMap(field.getName(), copy.value()) : fieldMap(copy.value(),
                            field.getName());
                    if (!"".equals(copy.converterId())) {
                        fieldMap.converter(copy.converterId());
                    }
                    if (copy.writeOnly())
                        fieldMap.bToA();
                    fieldMap.add();
                }

            }

            if (mapped.byDefault())
                super.byDefault(withDefaults);
        } else {
            super.byDefault(withDefaults);
        }

        return this;
    }

    public static class Factory extends ClassMapBuilderFactory {

        @Override
        protected <A, B> ClassMapBuilder<A, B> newClassMapBuilder(Type<A> aType, Type<B> bType, MapperFactory mapperFactory, PropertyResolverStrategy propertyResolver,
                                                                  DefaultFieldMapper[] defaults) {
            return new AnnotationClassMapBuilder<A, B>(aType, bType, mapperFactory, propertyResolver, defaults);
        }

    }

}



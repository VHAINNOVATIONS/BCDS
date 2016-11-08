package gov.va.vba.service.data;

import ma.glasnost.orika.MapperFacade;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public abstract class AbsDataService<I, O> {

    @Inject
    protected JpaRepository<I, Long> repository;

    @Inject
    protected MapperFacade mapper = null;

    protected Class<I> inputClass;
    protected Class<O> outputClass;

    public void setClasses(Class<I> inputClass, Class<O> outputClass) {
        this.inputClass = inputClass;
        this.outputClass = outputClass;
    }

    public O findOne(java.lang.Long aLong) {
        I obj = repository.findOne(aLong);
        return mapper.map(obj, outputClass);
    }

    public I findOneEntity(java.lang.Long aLong) {
        return repository.findOne(aLong);
    }


    public List<O> findAll() {
        List<O> output = new ArrayList<>();
        List<I> input = repository.findAll();
        mapper.mapAsCollection(input, output, outputClass);

        return output;
    }


    public List<O> findAll(org.springframework.data.domain.Sort sort) {
        List<O> output = new ArrayList<>();
        List<I> input = repository.findAll(sort);
        mapper.mapAsCollection(input, output, outputClass);

        return output;
    }


    public <S extends O> S save(S s) {
        I obj = mapper.map(s, inputClass);
        I savedObj = repository.save(obj);
        O outObj = mapper.map(savedObj, outputClass);

        return (S) outObj;
    }

    public <S extends O> S saveEntity(I obj) {
        I savedObj = repository.save(obj);
        O outObj = mapper.map(savedObj, outputClass);

        return (S) outObj;
    }


    public void deleteAll() {
        repository.deleteAll();
    }


    public long count() {
        return repository.count();
    }


    public boolean exists(java.lang.Long aLong) {
        return repository.exists(aLong);
    }


    public void delete(java.lang.Long aLong) {
        repository.delete(aLong);
    }


    public void delete(O o) {
        I obj = mapper.map(o, inputClass);
        repository.delete(obj);
    }

    public I mapToEntity(O o) {
        I obj = mapper.map(o, inputClass);
        return obj;
    }

    public O mapToDomain(I i) {
        O obj = mapper.map(i, outputClass);
        return obj;
    }

}

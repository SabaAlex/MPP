package services;

import model.domain.BaseEntity;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import model.validators.Validator;
import repository.IRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class BaseService<ID extends Serializable, T extends BaseEntity<ID>> implements IService<ID, T> {
    protected IRepository<ID, T> repository;
    protected Validator<T> validator;
    private String className;
    protected ExecutorService executorService;


    public void setClassName(String className) {
        this.className = className;
    }

    public BaseService(IRepository<ID,T> repository, Validator<T> validator, String className) {
        this.validator=validator;
        this.repository=repository;
        this.className = className;
    }

    @Override
    public synchronized Optional<T> FindOne(ID id) {
        return this.repository.findOne(id);
    }

    @Override
    public synchronized void addEntity(T entity) throws MyException {

        validator.validate(entity);
        Optional<T> entityOpt = repository.save(entity);
        entityOpt.ifPresent(optional -> {
            throw new MyException(
                    this.className + " already exists");
        });
    }

    @Override
    public synchronized T updateEntity(T entity) throws MyException {
        validator.validate(entity);
        return repository.update(entity).orElseThrow(()-> new MyException(this.className + " does not exist"));
    }

    @Override
    public synchronized T deleteEntity(ID id) throws ValidatorException {
        return repository.delete(id).orElseThrow(()-> new MyException(this.className +" with that ID does not exist"));
    }

    @Override
    public synchronized Set<T> getAllEntities() {
        Iterable<T> entities = repository.findAll();
        return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toSet());
    }

    @Override
    public  abstract List<T> getAllEntitiesSorted();


}

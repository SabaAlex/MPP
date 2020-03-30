package services;

import model.domain.BaseEntity;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import model.validators.Validator;
import repository.IRepository;
import repository.SavesToFile;
import repository.Sort;
import repository.SortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class BaseService<ID, T extends BaseEntity<ID>> implements IService<ID, T> {
    protected IRepository<ID, T> repository;
    private Validator<T> validator;
    private String className;
    public BaseService(IRepository<ID,T> repository, Validator<T> validator, String className) {
        this.validator=validator;
        this.repository=repository;
        this.className = className;
    }

    @Override
    public Optional<T> FindOne(ID id) {
        return this.repository.findOne(id);
    }

    @Override
    public void addEntity(T entity) throws MyException {
        validator.validate(entity);
        repository.save(entity).ifPresent(optional->{throw new MyException(this.className + " already exists");});
    }

    @Override
    public T updateEntity(T entity) throws MyException {
        validator.validate(entity);
        return repository.update(entity).orElseThrow(()-> new MyException(this.className + " does not exist"));
    }

    @Override
    public T deleteEntity(ID id) throws ValidatorException {
        return repository.delete(id).orElseThrow(()-> new MyException(this.className +" with that ID does not exist"));
    }

    @Override
    public Set<T> getAllEntities() {
        Iterable<T> entities = repository.findAll();
        return StreamSupport.stream(entities.spliterator(),false).collect(Collectors.toSet());
    }

    @Override
    public List<T> getAllEntitiesSorted(Sort sort) {
        if(repository instanceof SortingRepository)
        {
            Iterable<T> entities=((SortingRepository<ID, T>) repository).findAll(sort);
            return StreamSupport.stream(entities.spliterator(),false).collect(Collectors.toList());
        }
        throw new MyException("This is not A SUPPORTED SORTING REPOSITORY");
    }

    @Override
    public void saveToFile() {
        if (repository instanceof SavesToFile){
            ((SavesToFile)repository).saveToFile();
        }
    }
}

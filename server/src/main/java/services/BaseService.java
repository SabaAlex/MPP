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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class BaseService<ID, T extends BaseEntity<ID>> implements IService<ID, T> {
    protected IRepository<ID, T> repository;
    private Validator<T> validator;
    private String className;
    protected ExecutorService executorService;


    public BaseService(IRepository<ID,T> repository, Validator<T> validator, String className, ExecutorService executor) {
        this.validator=validator;
        this.repository=repository;
        this.className = className;
        this.executorService = executorService;
    }

    @Override
    public Optional<T> FindOne(ID id) {
        return this.repository.findOne(id);
    }

    @Override
    public Future< T > addEntity(T entity) throws MyException {
        validator.validate(entity);
        T add_entity=repository.save(entity).orElseThrow(()-> new MyException("No "+this.className+" to add"));
        return executorService.submit( ()->add_entity);
    }

    @Override
    public Future<T> updateEntity(T entity) throws MyException {
        validator.validate(entity);
        T update_Entity = repository.update(entity).orElseThrow(()-> new MyException(this.className + " does not exist"));
        return executorService.submit(()->update_Entity);
    }

    @Override
    public Future<T> deleteEntity(ID id) throws ValidatorException {
        T delete_entity = repository.delete(id).orElseThrow(()-> new MyException(this.className +" with that ID does not exist"));
        return executorService.submit(() -> delete_entity);
    }

    @Override
    public Future<Set<T>> getAllEntities() {
        Iterable<T> entities = repository.findAll();
        Set<T> entity_Set = StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toSet());
        return executorService.submit(() -> entity_Set);
    }

    @Override
    public void saveToFile() {
        if (repository instanceof SavesToFile){
            ((SavesToFile)repository).saveToFile();
        }
    }
}

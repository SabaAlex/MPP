package Service;

import model.domain.BaseEntity;
import model.domain.Client;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.postgreSQL.jpa.BaseJPARepository;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public abstract class BaseService<ID extends Serializable, T extends BaseEntity<ID>> implements IService<ID, T> {

    protected Logger logger;
    protected BaseJPARepository<T, ID> repository ;
    protected String serviceClassName;

    @Override
    public synchronized Optional<T> FindOne(ID id) {
        return this.repository.findById(id);
    }

    @Override
    @Transactional
    public synchronized void addEntity(T entity) throws MyException {
        repository.findById(entity.getId()).ifPresent(optional -> {
            throw new MyException(
                    this.serviceClassName + " already exists");
        });
        repository.save(entity);
    }

    @Override
    public synchronized T updateEntity(T entity) throws MyException {
        if (!repository.existsById(entity.getId()))
            throw new MyException(this.serviceClassName + " does not exist");
        repository.save(entity);
        return entity;
    }

    @Override
    public synchronized T deleteEntity(ID id) throws ValidatorException {
        Optional<T> entity = repository.findById(id);
        entity.orElseThrow(()-> new MyException(this.serviceClassName + " with that ID does not exist"));
        repository.deleteById(id);
        return entity.get();
    }

    @Override
    @Transactional
    public synchronized Set<T> getAllEntities() {
        Iterable<T> entities = repository.findAll();
        return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toSet());
    }
}

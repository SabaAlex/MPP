package core.Service;

import core.model.domain.BaseEntity;
import core.repository.postgreSQL.jpa.BaseJPARepository;
import core.model.exceptions.MyException;
import core.model.exceptions.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        logger.trace("FindOne - method entered:" + this.serviceClassName + "id = {}", id);
        return this.repository.findById(id);
    }

    @Override
    @Transactional
    public synchronized void addEntity(T entity) throws MyException {
        logger.trace("addEntity - method entered:" + this.serviceClassName + " = {}", entity);
        repository.findById(entity.getId()).ifPresent(optional -> {
            logger.debug("addEntity - exists: " + this.serviceClassName + " = {}", entity);
            throw new MyException(
                    this.serviceClassName + " already exists");
        });
        repository.save(entity);
        logger.trace("addEntity - method finished");
    }

    @Override
    @Transactional
    public synchronized T updateEntity(T entity) throws MyException {
        logger.trace("updateEntity - method entered:" + this.serviceClassName + " = {}", entity);
        if (!repository.existsById(entity.getId())) {
            logger.debug("updateEntity - does not exists: " + this.serviceClassName + " = {}", entity);
            throw new MyException(this.serviceClassName + " does not exist");
        }
        repository.save(entity);
        logger.trace("updateEntity - method finished");
        return entity;
    }

    @Override
    @Transactional
    public synchronized T deleteEntity(ID id) throws ValidatorException {
        logger.trace("deleteEntity - method entered:" + this.serviceClassName + "id = {}", id);
        Optional<T> entity = repository.findById(id);
        entity.orElseThrow(()-> new MyException(this.serviceClassName + " with that ID does not exist"));
        repository.deleteById(id);
        logger.trace("deleteEntity - method finished");
        return entity.get();
    }

    @Override
    public synchronized Set<T> getAllEntities() {
        logger.trace("getAllEntities - method entered");
        Iterable<T> entities = repository.findAll();
        logger.trace("getAllEntities - method finished, list: " + entities.toString());
        return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toSet());
    }
}

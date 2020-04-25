package repository;


import model.domain.BaseEntity;

import java.io.Serializable;

/**
 * Created by radu.
 */
public interface SortingRepository<ID extends Serializable, T extends BaseEntity<ID>>
                                    extends IRepository<ID, T> {

    Iterable<T> findAll(Sort sort);

}

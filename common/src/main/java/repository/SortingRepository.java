package repository;


import model.domain.BaseEntity;

/**
 * Created by radu.
 */
public interface SortingRepository<ID, T extends BaseEntity<ID>>
                                    extends IRepository<ID, T> {

    Iterable<T> findAll(Sort sort);

}

package Service;

import model.domain.BaseEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public abstract class BaseService<ID extends Serializable, T extends BaseEntity<ID>> implements IService<ID, T> {

}

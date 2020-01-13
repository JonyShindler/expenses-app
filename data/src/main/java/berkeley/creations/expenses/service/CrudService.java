package berkeley.creations.expenses.service;

import java.util.Set;

public interface CrudService<T, ID>{

    Set<T> findAll();

    T findById(ID Id);

    T save(T t);

    void delete(T t);

    void deleteById(ID id);
}

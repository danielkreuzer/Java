package swt6.spring.worklog.dao;

import swt6.spring.worklog.domain.Employee;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, ID extends Serializable> {
    T findById(Long id);

    List<T> findAll();

    void insert(T entity);

    T merge(T entity);
}

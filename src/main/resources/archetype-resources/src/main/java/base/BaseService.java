package ${groupId}.base;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<T, ID extends Serializable> {
    List<T> findAll();

    Page<T> findAll(Pageable var1);

    Page<T> findAll(Specification<T> specification, Pageable pageable);

    List<T> saveAll(Iterable<T> var1);

    T save(T var1);

    void deleteInBatch(Iterable<T> var1);

    T getOne(ID var1);

    Optional<T> findOne(Specification<T> specification);

    Optional<T> findOne(Example<T> example);

    Optional<T> findById(ID var1);

    void deleteById(ID var1);

    void delete(T entity);

    long count();

    long count(Specification<T> specification);

    long count(Example<T> example);

    void executeSql(String... sqls);

}

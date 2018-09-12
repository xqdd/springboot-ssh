package ${groupId}.base.impl;

import ${groupId}.base.BaseRepository;
import ${groupId}.base.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

    /**
     * 复用日志
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private BaseRepository<T, ID> baseRepository;

    public BaseServiceImpl(BaseRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    public List<T> findAll() {
        return this.baseRepository.findAll();
    }

    public Page<T> findAll(Pageable pageable) {
        return this.baseRepository.findAll(pageable);
    }

    @Override
    public List<T> findAll(Specification<T> specification) {
        return baseRepository.findAll(specification);
    }

    @Override
    public Page<T> findAll(Specification<T> specification,Pageable pageable) {
        return this.baseRepository.findAll(specification,pageable);
    }

    public List<T> saveAll(Iterable<T> entities) {
        this.baseRepository.resetId();
        return this.baseRepository.saveAll(entities);
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        return baseRepository.findAllById(ids);
    }

    public T save(T entity) {
//        this.baseRepository.resetId();
        return this.baseRepository.save(entity);
    }

    public void deleteInBatch(Iterable<T> entities) {
        this.baseRepository.deleteInBatch(entities);
    }

    public T getOne(ID id) {
        return this.baseRepository.getOne(id);
    }

    @Override
    public Optional<T> findOne(Specification<T> specification) {
        return baseRepository.findOne(specification);
    }

    @Override
    public Optional<T> findOne(Example<T> example) {
        return baseRepository.findOne(example);
    }

    public Optional<T> findById(ID id) {
        return this.baseRepository.findById(id);
    }

    @Override
    public void deleteById(ID id) {
        this.baseRepository.deleteById(id);
    }

    @Override
    public void delete(T entity) {
        this.baseRepository.delete(entity);
    }

    public long count() {
        return this.baseRepository.count();
    }

    public long count(Specification<T> specification) {
        return this.baseRepository.count(specification);
    }

    public long count(Example<T> example) {
        return this.baseRepository.count(example);
    }

    @Override
    public void executeSql(String... sqls) {
        baseRepository.executeSql(sqls);
    }


}

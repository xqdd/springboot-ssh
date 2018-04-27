package ${groupId}.repository;

import ${groupId}.base.BaseRepository;
import ${groupId}.bean.entity.Admin;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepository extends BaseRepository<Admin, Integer> {

    Admin findByNameAndPassword(String name, String password);


    Admin findByIdAndPassword(Integer id, String password);
}

package ${groupId}.mvc.repository;

import ${groupId}.base.BaseRepository;
import ${groupId}.mvc.bean.entity.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends BaseRepository<User, String> {

}

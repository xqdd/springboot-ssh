package ${groupId}.mvc.repository;

import ${groupId}.base.BaseRepository;
import ${groupId}.mvc.bean.entity.Business;
import ${groupId}.mvc.bean.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BusinessRepository extends BaseRepository<Business, Integer> {

    Optional<Business> findByUser(User user);

}

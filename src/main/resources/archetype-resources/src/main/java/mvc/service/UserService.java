package ${groupId}.mvc.service;

import ${groupId}.base.BaseService;
import ${groupId}.base.impl.BaseServiceImpl;
import ${groupId}.mvc.bean.entity.User;
import ${groupId}.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService extends BaseServiceImpl<User, String> implements BaseService<User, String> {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }
}

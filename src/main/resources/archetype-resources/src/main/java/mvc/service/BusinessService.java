package ${groupId}.mvc.service;

import ${groupId}.base.BaseService;
import ${groupId}.base.impl.BaseServiceImpl;
import ${groupId}.mvc.bean.entity.Business;
import ${groupId}.mvc.bean.entity.User;
import ${groupId}.mvc.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BusinessService extends BaseServiceImpl<Business, Integer> implements BaseService<Business, Integer> {

    private BusinessRepository businessRepository;

    @Autowired
    public BusinessService(BusinessRepository businessRepository) {
        super(businessRepository);
        this.businessRepository = businessRepository;
    }


    public Optional<Business> findByUser(User user) {
        return businessRepository.findByUser(user);
    }
}

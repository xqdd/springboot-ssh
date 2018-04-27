package ${groupId}.service;

import ${groupId}.base.impl.BaseServiceImpl;
import ${groupId}.bean.entity.Admin;
import ${groupId}.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminService extends BaseServiceImpl<Admin, Integer> implements ${groupId}.base.BaseService<Admin, Integer> {

    private AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        super(adminRepository);
        this.adminRepository = adminRepository;
    }

    public Admin findByNameAndPassword(String name, String password) {
        return adminRepository.findByNameAndPassword(name, password);
    }

    public Admin findByIdAndPassword(Integer id, String password) {
        return adminRepository.findByIdAndPassword(id, password);
    }


}

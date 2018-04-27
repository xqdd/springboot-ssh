package ${groupId}.config;

import ${groupId}.bean.entity.Admin;
import ${groupId}.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig implements ApplicationListener<ContextRefreshedEvent> {


    private final AdminService adminService;


    @Autowired
    public ApplicationConfig(AdminService adminService) {
        this.adminService = adminService;

    }

    //启动时运行
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (adminService.count() == 0) {
            Admin admin = new Admin();
            admin.setName("yuequan");
            admin.setPassword("yuequan");
            adminService.save(admin);
        }
    }
}

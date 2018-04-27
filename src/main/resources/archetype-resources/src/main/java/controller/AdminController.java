package ${groupId}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequestMapping(value = "admin", produces = "application/json; charset=utf-8")
@RestController
public class AdminController {

    private final ${groupId}.service.AdminService adminService;

    private final HttpSession session;

    @Autowired
    public AdminController(${groupId}.service.AdminService adminService, HttpSession session) {
        this.adminService = adminService;
        this.session = session;
    }

    /**
     * 管理员登陆
     *
     * @param admin
     * @return
     */
    @RequestMapping("login")
    public ${groupId}.bean.Msg login(@Valid ${groupId}.bean.entity.Admin admin, String vCode) {
        //验证码校验
        if (vCode == null || !vCode.equals(session.getAttribute("vCode"))) {
            return ${groupId}.bean.Msg.failed("vCode", "验证码有误");
        } else {
            session.removeAttribute("vCode");
        }
        ${groupId}.bean.entity.Admin admin1 = adminService.findByNameAndPassword(admin.getName(), admin.getPassword());
        if (admin1 == null) return ${groupId}.bean.Msg.failed("msg", "管理员账号或密码有误");
        session.setAttribute("admin", admin1);
        return ${groupId}.bean.Msg.success("登陆成功");
    }


    /**
     * 管理员注销登陆
     *
     * @return
     */
    @RequestMapping("logout")
    public ${groupId}.bean.Msg logout() {
        session.removeAttribute("admin");
        return ${groupId}.bean.Msg.success("注销登陆成功");
    }


    /**
     * 获取管理员信息
     *
     * @return
     */
    @RequestMapping("info")
    public ${groupId}.bean.Msg info() {
        return ${groupId}.bean.Msg.success(session.getAttribute("admin"));
    }

    /**
     * 修改密码
     *
     * @param newPassword
     * @param oldPassword
     * @return
     */
    @RequestMapping("updatePassword")
    public ${groupId}.bean.Msg updateInfo(String newPassword, String oldPassword) {
        if (${groupId}.utils.CommUtils.isBlank(oldPassword)) return ${groupId}.bean.Msg.failed("oldPassword", "旧密码不能为空");
        if (${groupId}.utils.CommUtils.isBlank(newPassword)) return ${groupId}.bean.Msg.failed("newPassword", "新密码不能为空");
        ${groupId}.bean.entity.Admin admin = (${groupId}.bean.entity.Admin) session.getAttribute("admin");
        if (!admin.getPassword().equals(oldPassword)) return ${groupId}.bean.Msg.failed("oldPassword", "旧密码有误");
        admin.setPassword(newPassword);
        adminService.save(admin);
        session.setAttribute("admin", admin);
        return ${groupId}.bean.Msg.success("修改密码成功");
    }


    /**
     * 修改管理员名
     *
     * @param name
     * @return
     */
    @RequestMapping("updateName")
    public ${groupId}.bean.Msg updateName(String name) {
        if (${groupId}.utils.CommUtils.isBlank(name)) return ${groupId}.bean.Msg.failed("name", "管理员名不能为空");
        ${groupId}.bean.entity.Admin admin = (${groupId}.bean.entity.Admin) session.getAttribute("admin");
        admin.setName(name);
        adminService.save(admin);
        session.setAttribute("admin", admin);
        return ${groupId}.bean.Msg.success("修改管理员名成功");
    }


}

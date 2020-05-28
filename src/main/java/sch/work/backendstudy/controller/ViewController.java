package sch.work.backendstudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sch.work.backendstudy.constants.GlobalConstant;
import sch.work.backendstudy.domain.AdminEntity;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/view")
public class ViewController {
    @RequestMapping("/index")
    public String adminIndex(Model model, HttpServletRequest request) {
        AdminEntity adminEntity = (AdminEntity) request.getSession().getAttribute(GlobalConstant.ADMIN_INFO_KEY);
        model.addAttribute("admin", adminEntity);
        return "/adminIndex";
    }

    @RequestMapping("/login")
    public String adminLogin() {
        return "/adminLogin";
    }
}

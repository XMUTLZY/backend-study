package sch.work.backendstudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sch.work.backendstudy.http.request.AdminRequest;
import sch.work.backendstudy.http.response.BaseResponse;
import sch.work.backendstudy.http.response.LayerResponse;
import sch.work.backendstudy.http.vo.Admin;
import sch.work.backendstudy.service.AdminService;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //管理员登录
    @PostMapping("/login")
    @ResponseBody
    public BaseResponse login(@RequestBody AdminRequest request, HttpServletRequest httpServletRequest) {
        return adminService.login(request, httpServletRequest);
    }

    //添加管理员
    @PostMapping("/add")
    @ResponseBody
    public BaseResponse add(@RequestBody AdminRequest request) {
        return adminService.add(request);
    }

    //管理员列表
    @GetMapping("/admin-list")
    @ResponseBody
    public LayerResponse adminList(@RequestParam("limit") Integer limit, @RequestParam("page") Integer page) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.Direction.ASC, "id");
        return adminService.adminList(pageable);
    }

    //删除管理员
    @GetMapping("/admin-delete")
    @ResponseBody
    public BaseResponse adminDelete(@RequestParam Integer id) {
        return adminService.adminDelete(id);
    }

    //获取指定管理员
    @GetMapping("/admin-get")
    @ResponseBody
    public BaseResponse adminGet(@RequestParam Integer id) {
        return adminService.adminGet(id);
    }

    //修改管理员信息
    @PostMapping("/admin-update")
    @ResponseBody
    public BaseResponse adminUpdate(@RequestBody Admin admin) {
        return adminService.adminUpdate(admin);
    }

    //添加管理员
    @PostMapping("/admin-add")
    @ResponseBody
    public BaseResponse adminAdd(@RequestBody Admin admin) {
        return adminService.adminAdd(admin);
    }
}

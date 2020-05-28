package sch.work.backendstudy.service;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sch.work.backendstudy.constants.GlobalConstant;
import sch.work.backendstudy.constants.SystemUtils;
import sch.work.backendstudy.domain.AdminEntity;
import sch.work.backendstudy.http.request.AdminRequest;
import sch.work.backendstudy.http.response.BaseResponse;
import sch.work.backendstudy.http.response.LayerResponse;
import sch.work.backendstudy.http.vo.Admin;
import sch.work.backendstudy.repository.AdminRepository;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public BaseResponse login(AdminRequest request, HttpServletRequest httpServletRequest) {
        BaseResponse response = new BaseResponse();
        if (!StringUtils.hasText(request.getAccountName())) {
            SystemUtils.buildErrorReponse(response, "请输入管理员用户名");
        }
        AdminEntity adminEntity = adminRepository.findByAccountName(request.getAccountName());
        if (StringUtils.hasText(request.getPassword())) {
            String encodePassword = new Md5Hash(request.getPassword()).toString();
            if (adminEntity.getPassword().equals(encodePassword)) {
                response.setMessage("登录成功");
                httpServletRequest.getSession().setAttribute(GlobalConstant.ADMIN_INFO_KEY, adminEntity);
            } else {
                SystemUtils.buildErrorReponse(response, "密码错误");
            }
        } else {
            SystemUtils.buildErrorReponse(response, "请输入密码");
        }
        return response;
    }

    public BaseResponse add(AdminRequest request) {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setAccountName(request.getAccountName());
        adminEntity.setPassword(new Md5Hash(request.getPassword()).toString());
        adminEntity.setRole(AdminEntity.ROLE_COMMON);
        Date date = new Date();
        adminEntity.setUpdateTime(date);
        adminEntity.setCreateTime(date);
        adminRepository.save(adminEntity);
        return new BaseResponse();
    }

    public LayerResponse adminList(Pageable pageable) {
        LayerResponse response = new LayerResponse();
        Page<AdminEntity> adminEntityPage = adminRepository.findAllByRoleAndStatus(pageable, AdminEntity.ROLE_COMMON, GlobalConstant.STATUS_YES);
        List<Admin> adminList = new ArrayList<>();
        for (AdminEntity adminEntity : adminEntityPage) {
            Admin admin = new Admin();
            BeanUtils.copyProperties(adminEntity, admin);
            if (adminEntity.getStatus() == GlobalConstant.STATUS_YES) {
                admin.setStatusStr("正常");
            } else {
                admin.setStatusStr("已删除");
            }
            if (adminEntity.getRole() == AdminEntity.ROLE_COMMON) {
                admin.setRoleName("普通管理员");
            }
            adminList.add(admin);
        }
        response.setData(adminList);
        response.setCount(adminRepository.findAllByStatusAndRole(GlobalConstant.STATUS_YES, AdminEntity.ROLE_COMMON).size());
        return response;
    }

    public BaseResponse adminDelete(Integer id) {
        AdminEntity adminEntity = adminRepository.findById(id).get();
        adminEntity.setStatus(GlobalConstant.STATUS_NO);
        adminEntity.setUpdateTime(new Date());
        adminRepository.save(adminEntity);
        return new BaseResponse();
    }

    public BaseResponse adminGet(Integer id) {
        AdminEntity adminEntity = adminRepository.findById(id).get();
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminEntity, admin);
        BaseResponse response = new BaseResponse();
        response.setVo(admin);
        return response;
    }

    public BaseResponse adminUpdate(Admin admin) {
        AdminEntity adminEntity = adminRepository.findById(admin.getId()).get();
        adminEntity.setUpdateTime(new Date());
        if (!admin.getPassword().equals(adminEntity.getPassword())) {
            adminEntity.setPassword(new Md5Hash(admin.getPassword()).toString());
        }
        adminEntity.setAccountName(admin.getAccountName());
        adminRepository.save(adminEntity);
        return new BaseResponse();
    }

    public BaseResponse adminAdd(Admin admin) {
        Date date = new Date();
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setAccountName(admin.getAccountName());
        adminEntity.setPassword(new Md5Hash(admin.getPassword()).toString());
        adminEntity.setUpdateTime(date);
        adminEntity.setCreateTime(date);
        adminEntity.setStatus(GlobalConstant.STATUS_YES);
        adminEntity.setRole(AdminEntity.ROLE_COMMON);
        adminRepository.save(adminEntity);
        return new BaseResponse();
    }
}

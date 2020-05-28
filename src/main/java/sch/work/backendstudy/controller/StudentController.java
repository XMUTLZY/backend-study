package sch.work.backendstudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sch.work.backendstudy.http.response.BaseResponse;
import sch.work.backendstudy.http.response.LayerResponse;
import sch.work.backendstudy.service.StudentService;
import java.io.IOException;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    //获取所有学生信息
    @GetMapping("/student-list")
    @ResponseBody
    public LayerResponse studentList(@RequestParam("limit") Integer limit, @RequestParam("page") Integer page) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.Direction.ASC, "id");
        return studentService.studentList(pageable);
    }

    //导入学生名单
    @PostMapping("/import-list")
    @ResponseBody
    public BaseResponse importList(MultipartFile file) throws IOException {
        return studentService.importList(file);
    }
}

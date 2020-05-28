package sch.work.backendstudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sch.work.backendstudy.http.response.LayerResponse;
import sch.work.backendstudy.service.AnswerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    //学生答题情况
    @GetMapping("/answer-list")
    @ResponseBody
    public LayerResponse answerList(@RequestParam("limit") Integer limit, @RequestParam("page") Integer page) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.Direction.ASC, "id");
        return answerService.answerList(pageable);
    }

    //导出学生答题情况
    @GetMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
        answerService.export(request, response);
    }
}

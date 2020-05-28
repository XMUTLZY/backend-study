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
import sch.work.backendstudy.http.response.BaseResponse;
import sch.work.backendstudy.http.response.LayerResponse;
import sch.work.backendstudy.http.vo.Question;
import sch.work.backendstudy.service.QuestionService;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    //获取当前登录管理员的所有题目
    @GetMapping("/question-list")
    @ResponseBody
    public LayerResponse questionList(@RequestParam("limit") Integer limit, @RequestParam("page") Integer page, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.Direction.ASC, "id");
        return questionService.questionList(pageable, request);
    }

    //删除题目
    @GetMapping("/question-delete")
    @ResponseBody
    public BaseResponse questionDelete(@RequestParam("question_id") Integer questionId) {
        return questionService.questionDelete(questionId);
    }

    //获取题目
    @GetMapping("/question-get")
    @ResponseBody
    public BaseResponse questionGet(@RequestParam("question_id") Integer questionId) {
        return questionService.questionGet(questionId);
    }

    //修改题目
    @PostMapping("/question-update")
    @ResponseBody
    public BaseResponse questionUpdate(@RequestBody Question question) {
        return questionService.questionUpdate(question);
    }

    //增加题目
    @PostMapping("/question-add")
    @ResponseBody
    public BaseResponse questionAdd(@RequestBody Question question, HttpServletRequest request) {
        return questionService.questionAdd(question, request);
    }

    //获取当前管理员所管理的课程
    @PostMapping("/project-list")
    @ResponseBody
    public BaseResponse questionProjectList(HttpServletRequest request) {
        return questionService.questionProjectList(request);
    }

}

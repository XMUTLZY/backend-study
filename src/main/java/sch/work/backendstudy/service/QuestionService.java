package sch.work.backendstudy.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sch.work.backendstudy.constants.GlobalConstant;
import sch.work.backendstudy.domain.AdminEntity;
import sch.work.backendstudy.domain.ProjectEntity;
import sch.work.backendstudy.domain.QuestionEntity;
import sch.work.backendstudy.http.response.BaseResponse;
import sch.work.backendstudy.http.response.LayerResponse;
import sch.work.backendstudy.http.vo.Question;
import sch.work.backendstudy.repository.ProjectRepository;
import sch.work.backendstudy.repository.QuestionRepository;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public LayerResponse questionList(Pageable pageable, HttpServletRequest request) {
        LayerResponse response = new LayerResponse();
        AdminEntity adminEntity = (AdminEntity) request.getSession().getAttribute(GlobalConstant.ADMIN_INFO_KEY);
        //查询该教师教的全部课程
        List<ProjectEntity> projectEntityList = projectRepository.findAllByAdminIdAndStatus(adminEntity.getId(), GlobalConstant.STATUS_YES);
        //获取课程id列表
        List<Integer> projectIdList = projectEntityList.stream().map(ProjectEntity::getId).collect(Collectors.toList());
        //获取全部课程
        Page<QuestionEntity> questionEntityPage = questionRepository.findAllByProjectIdIn(pageable, projectIdList);
        List<Question> questionList = new ArrayList<>();
        for (QuestionEntity questionEntity : questionEntityPage) {
            Question question = new Question();
            buildQuestion(questionEntity, question);
            questionList.add(question);
        }
        //设置分页参数
        response.setCount(questionRepository.findAllByProjectIdIn(projectIdList).size());
        response.setData(questionList);
        return response;
    }

    public BaseResponse questionDelete(Integer questionId) {
        BaseResponse response = new BaseResponse();
        QuestionEntity questionEntity = questionRepository.findById(questionId).get();
        questionEntity.setStatus(GlobalConstant.STATUS_NO);
        questionRepository.save(questionEntity);
        return response;
    }

    public BaseResponse questionGet(Integer questionId) {
        BaseResponse response = new BaseResponse();
        QuestionEntity questionEntity = questionRepository.findById(questionId).get();
        Question question = new Question();
        buildQuestion(questionEntity, question);
        response.setVo(question);
        return response;
    }

    private void buildQuestion(QuestionEntity questionEntity, Question question) {
        BeanUtils.copyProperties(questionEntity, question);
        if (questionEntity.getStatus() == GlobalConstant.STATUS_YES) {
            question.setStatusStr("正常");
        } else {
            question.setStatusStr("已删除");
        }
        ProjectEntity projectEntity = projectRepository.findByIdAndStatus(questionEntity.getProjectId(), GlobalConstant.STATUS_YES);
        if (projectEntity != null) {
            question.setProjectName(projectEntity.getProjectName());
        }
    }

    public BaseResponse questionUpdate(Question question) {
        QuestionEntity questionEntity = questionRepository.findById(question.getId()).get();
        questionEntity.setAnswerA(question.getAnswerA());
        questionEntity.setAnswerB(question.getAnswerB());
        questionEntity.setAnswerC(question.getAnswerC());
        questionEntity.setAnswerD(question.getAnswerD());
        questionEntity.setCorrectAnswer(question.getCorrectAnswer());
        questionEntity.setQuestionText(question.getQuestionText());
        questionRepository.save(questionEntity);
        return new BaseResponse();
    }

    @Transactional
    public BaseResponse questionAdd(Question question, HttpServletRequest request) {
        //判断是否有该科目
        AdminEntity adminEntity = (AdminEntity) request.getSession().getAttribute(GlobalConstant.ADMIN_INFO_KEY);
        List<ProjectEntity> projectEntityList = projectRepository.findAllByAdminIdAndStatus(adminEntity.getId(), GlobalConstant.STATUS_YES);
        List<Integer> projectIdList = projectEntityList.stream().map(ProjectEntity::getId).collect(Collectors.toList());
        if (!projectIdList.contains(question.getProjectId())) { //没有该科目 则添加进数据库
            ProjectEntity projectEntity = new ProjectEntity();
            projectEntity.setAdminId(adminEntity.getId());
            projectEntity.setProjectName(question.getProjectName());
            projectEntity.setStatus(GlobalConstant.STATUS_YES);
            projectRepository.save(projectEntity);
        }
        BaseResponse response = new BaseResponse();
        QuestionEntity questionEntity = new QuestionEntity();
        BeanUtils.copyProperties(question, questionEntity);
        questionEntity.setStatus(GlobalConstant.STATUS_YES);
        questionRepository.save(questionEntity);
        return response;
    }

    public BaseResponse questionProjectList(HttpServletRequest request) {
        BaseResponse response = new BaseResponse();
        AdminEntity adminEntity = (AdminEntity) request.getSession().getAttribute(GlobalConstant.ADMIN_INFO_KEY);
        List<ProjectEntity> projectEntityList = projectRepository.findAllByAdminIdAndStatus(adminEntity.getId(), GlobalConstant.STATUS_YES);
        response.setVo(projectEntityList);
        return response;
    }

}

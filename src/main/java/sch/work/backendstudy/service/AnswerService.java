package sch.work.backendstudy.service;

import com.csvreader.CsvWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sch.work.backendstudy.constants.GlobalConstant;
import sch.work.backendstudy.constants.SystemUtils;
import sch.work.backendstudy.domain.QuestionEntity;
import sch.work.backendstudy.domain.StudentAnswerEntity;
import sch.work.backendstudy.domain.StudentEntity;
import sch.work.backendstudy.http.response.LayerResponse;
import sch.work.backendstudy.http.vo.Answer;
import sch.work.backendstudy.repository.QuestionRepository;
import sch.work.backendstudy.repository.StudentAnswerRepository;
import sch.work.backendstudy.repository.StudentRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private StudentAnswerRepository studentAnswerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private StudentRepository studentRepository;

    public LayerResponse answerList(Pageable pageable) {
        LayerResponse layerResponse = new LayerResponse();
        Page<StudentAnswerEntity> studentAnswerEntityPage = studentAnswerRepository.findAllByStatus(pageable, GlobalConstant.STATUS_YES);
        List<Answer> answerList = new ArrayList<>();
        for (StudentAnswerEntity studentAnswerEntity : studentAnswerEntityPage) {
            Answer answer = new Answer();
            buildAnswer(studentAnswerEntity, answer);
            answerList.add(answer);
        }
        layerResponse.setData(answerList);
        layerResponse.setCount(studentAnswerRepository.findAllByStatus(GlobalConstant.STATUS_YES).size());
        return layerResponse;
    }

    public void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<StudentAnswerEntity> studentAnswerEntityList = studentAnswerRepository.findAllByStatus(GlobalConstant.STATUS_YES);
        List<Answer> answerList = new ArrayList<>();
        for (StudentAnswerEntity studentAnswerEntity : studentAnswerEntityList) {
            Answer answer = new Answer();
            buildAnswer(studentAnswerEntity, answer);
            answerList.add(answer);
        }
        if (CollectionUtils.isEmpty(answerList)) {
            return;
        }
        String fileName = "学生答题情况";
        SystemUtils.initCsvFile(response, request, fileName);
        String[] header;
        header = new String[]{"ID", "学生姓名", "问题", "作答", "正确答案", "作答时间"};
        CsvWriter csvWriter = new CsvWriter(response.getOutputStream(), ',', Charset.forName("UTF-8"));
        csvWriter.writeRecord(header);
        String[] data;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Answer answer : answerList) {
            data = new String[] {
                    String.valueOf(answer.getId()),
                    String.valueOf(answer.getUserName()),
                    String.valueOf(answer.getQuestionText()),
                    String.valueOf(answer.getChooseAnswer()),
                    String.valueOf(answer.getCorrectAnswer()),
                    format.format(answer.getCreateTime())
            };
            csvWriter.writeRecord(data);
        }
        csvWriter.close();
        response.getOutputStream().flush();
    }

    private void buildAnswer(StudentAnswerEntity studentAnswerEntity, Answer answer) {
        BeanUtils.copyProperties(studentAnswerEntity, answer);
        if (studentAnswerEntity.getStatus() == GlobalConstant.STATUS_YES) {
            answer.setStatusStr("正常");
        } else {
            answer.setStatusStr("已删除");
        }
        QuestionEntity questionEntity = questionRepository.findById(studentAnswerEntity.getQuestionId()).get();
        if (questionEntity != null) {
            answer.setCorrectAnswer(questionEntity.getCorrectAnswer());
            answer.setQuestionText(questionEntity.getQuestionText());
        }
        StudentEntity studentEntity = studentRepository.findById(studentAnswerEntity.getUserId()).get();
        if (studentEntity != null) {
            answer.setUserName(studentEntity.getUserName());
        }
    }
}

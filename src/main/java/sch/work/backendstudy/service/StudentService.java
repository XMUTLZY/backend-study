package sch.work.backendstudy.service;

import com.csvreader.CsvReader;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sch.work.backendstudy.constants.GlobalConstant;
import sch.work.backendstudy.domain.StudentEntity;
import sch.work.backendstudy.http.response.BaseResponse;
import sch.work.backendstudy.http.response.LayerResponse;
import sch.work.backendstudy.http.vo.Student;
import sch.work.backendstudy.repository.StudentRepository;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public LayerResponse studentList(Pageable pageable) {
        LayerResponse response = new LayerResponse();
        Page<StudentEntity> studentEntityPage = studentRepository.findAll(pageable);
        List<Student> studentList = new ArrayList<>();
        for (StudentEntity studentEntity : studentEntityPage) {
            Student student = new Student();
            BeanUtils.copyProperties(studentEntity, student);
            if (studentEntity.getStatus() == GlobalConstant.STATUS_YES) {
                student.setStatusStr("正常");
            } else {
                student.setStatusStr("已删除");
            }
            studentList.add(student);
        }
        response.setData(studentList);
        response.setCount((int) studentRepository.count());
        return response;
    }

    public BaseResponse importList(MultipartFile file) throws IOException {
        BaseResponse response = new BaseResponse();
        List<StudentEntity> studentEntityList = new ArrayList();
        CsvReader csvReader = new CsvReader(file.getInputStream(), Charset.forName("GB2312"));
        csvReader.readHeaders();
        while (csvReader.readRecord()) {
            String userName = csvReader.get("姓名");
            String email = csvReader.get("邮箱");
            String password = csvReader.get("密码（明文）");
            StudentEntity studentEntity = new StudentEntity();
            studentEntity.setCreateTime(new Date());
            studentEntity.setEmail(email);
            studentEntity.setUserName(userName);
            studentEntity.setPassword(new Md5Hash(password).toString());
            studentEntity.setStatus(GlobalConstant.STATUS_YES);
            studentEntityList.add(studentEntity);
        }
        for (StudentEntity studentEntity : studentEntityList) {
            studentRepository.save(studentEntity);
        }
        return response;
    }
}

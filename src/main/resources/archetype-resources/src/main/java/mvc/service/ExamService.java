package ${groupId}.mvc.service;

import ${groupId}.mvc.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ExamService extends ${groupId}.base.impl.BaseServiceImpl<${groupId}.mvc.bean.entity.activity.Exam, Integer> implements ${groupId}.base.BaseService<${groupId}.mvc.bean.entity.activity.Exam, Integer> {

    private ExamRepository examRepository;

    @Autowired
    public ExamService(ExamRepository examRepository) {
        super(examRepository);
        this.examRepository = examRepository;
    }
}

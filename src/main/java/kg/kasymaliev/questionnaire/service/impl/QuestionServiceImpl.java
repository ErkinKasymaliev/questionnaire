package kg.kasymaliev.questionnaire.service.impl;

import kg.kasymaliev.questionnaire.entity.Question;
import kg.kasymaliev.questionnaire.entity.Questionnaire;
import kg.kasymaliev.questionnaire.repository.QuestionRepository;
import kg.kasymaliev.questionnaire.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    @Override
    public Optional<Question> getQuestion(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> getByQuestionnaire(Questionnaire questionnaire) {
        return questionRepository.findAllByQuestionnaire(questionnaire);
    }

    @Override
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Question question) {
        Question q = questionRepository.findById(question.getId()).orElse(null);
        if(q!=null)
            return questionRepository.save(question);
        return null;
    }

    @Override
    public boolean deleteQuestion(Question question) {
        Question q = questionRepository.findById(question.getId()).orElse(null);
        if(q==null)
            return false;

        questionRepository.delete(q);
        return true;
    }
}

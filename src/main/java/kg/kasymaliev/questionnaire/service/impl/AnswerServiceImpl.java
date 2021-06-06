package kg.kasymaliev.questionnaire.service.impl;

import kg.kasymaliev.questionnaire.entity.Answer;
import kg.kasymaliev.questionnaire.entity.Question;
import kg.kasymaliev.questionnaire.repository.AnswerRepository;
import kg.kasymaliev.questionnaire.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    @Override
    public Optional<Answer> getAnswer(Long id) {
        return answerRepository.findById(id);
    }

    @Override
    public List<Answer> getAnswersByQuestion(Question question) {
        return answerRepository.findAllByQuestion(question);
    }

    @Override
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    @Override
    public Answer addAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    @Transactional
    @Override
    public List<Answer> addAll(List<Answer> answers) {
        return answerRepository.saveAll(answers);
    }

    @Override
    public Answer updateAnswer(Answer answer) {
        Answer a = answerRepository.findById(answer.getId()).orElse(null);
        if(a!=null)
            return answerRepository.save(answer);
        return null;
    }

    @Override
    public boolean deleteAnswer(Answer answer) {
        Answer a = answerRepository.findById(answer.getId()).orElse(null);
        if(a!=null) {
            answerRepository.save(a);
            return true;
        }
        return false;
    }
}

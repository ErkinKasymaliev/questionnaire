package kg.kasymaliev.questionnaire.service;

import kg.kasymaliev.questionnaire.entity.Answer;
import kg.kasymaliev.questionnaire.entity.Question;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AnswerService {
    Optional<Answer> getAnswer(Long id);
    List<Answer> getAnswersByQuestion(Question question);
    List<Answer> getAllAnswers();
    Answer addAnswer(Answer answer);
    List<Answer> addAll(List<Answer> answers);
    Answer updateAnswer(Answer answer);
    boolean deleteAnswer(Answer answer);
}

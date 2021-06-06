package kg.kasymaliev.questionnaire.service;

import kg.kasymaliev.questionnaire.entity.Question;
import kg.kasymaliev.questionnaire.entity.Questionnaire;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Optional<Question> getQuestion(Long id);
    List<Question> getAllQuestions();
    List<Question> getByQuestionnaire(Questionnaire questionnaire);
    Question addQuestion(Question question);
    Question updateQuestion(Question question);
    boolean deleteQuestion(Question question);
}

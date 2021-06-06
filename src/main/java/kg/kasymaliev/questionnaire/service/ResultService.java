package kg.kasymaliev.questionnaire.service;

import kg.kasymaliev.questionnaire.entity.Question;
import kg.kasymaliev.questionnaire.entity.Result;
import kg.kasymaliev.questionnaire.entity.TakingSurvey;

import java.util.List;
import java.util.Optional;

public interface ResultService {
    Optional<Result> getResultById(long id);
    List<Result> getResultsByTakingSurvey(TakingSurvey takingSurvey);
    List<Result> getResultsByTakingSurveyAndQuestion(TakingSurvey takingSurvey, Question question);
    void addResult(Result result);
    void addAllResults(List<Result> result);
    Result updateResult(Result result);
    Result deleteResult(Long id);
}

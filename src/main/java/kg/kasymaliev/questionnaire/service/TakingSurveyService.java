package kg.kasymaliev.questionnaire.service;

import kg.kasymaliev.questionnaire.entity.Questionnaire;
import kg.kasymaliev.questionnaire.entity.TakingSurvey;
import kg.kasymaliev.questionnaire.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TakingSurveyService {
    Optional<TakingSurvey> getById(Long id);
    List<TakingSurvey> getByUser(User user);
    List<TakingSurvey> getByQuestionnaire(Questionnaire questionnaire);
    List<TakingSurvey> getAllTakingSurveys();
    List<TakingSurvey> getByCreatedDt(LocalDateTime createdDt);
    List<TakingSurvey> getByEndDt(LocalDateTime endDt);
    TakingSurvey addTakingSurvey(TakingSurvey takingSurvey);
    TakingSurvey updateTakingSurvey(TakingSurvey takingSurvey);
    boolean deleteTakingSurvey(Long id);
}

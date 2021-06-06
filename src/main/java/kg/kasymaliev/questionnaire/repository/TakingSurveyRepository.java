package kg.kasymaliev.questionnaire.repository;

import kg.kasymaliev.questionnaire.entity.Questionnaire;
import kg.kasymaliev.questionnaire.entity.TakingSurvey;
import kg.kasymaliev.questionnaire.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TakingSurveyRepository extends JpaRepository<TakingSurvey, Long> {
    List<TakingSurvey> findAllByUser(User user);
    List<TakingSurvey> findAllByQuestionnaire(Questionnaire questionnaire);
    List<TakingSurvey> findAllByCreatedDt(LocalDateTime createdDt);
    List<TakingSurvey> findAllByEndDt(LocalDateTime endDt);
}

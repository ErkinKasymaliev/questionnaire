package kg.kasymaliev.questionnaire.repository;

import kg.kasymaliev.questionnaire.entity.Question;
import kg.kasymaliev.questionnaire.entity.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByQuestionnaire(Questionnaire questionnaire);
}

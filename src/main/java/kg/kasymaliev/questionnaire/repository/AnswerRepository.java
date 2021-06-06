package kg.kasymaliev.questionnaire.repository;

import kg.kasymaliev.questionnaire.entity.Answer;
import kg.kasymaliev.questionnaire.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByQuestion(Question question);
}

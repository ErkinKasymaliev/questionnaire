package kg.kasymaliev.questionnaire.repository;

import kg.kasymaliev.questionnaire.entity.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
    Optional<Questionnaire> findByName(String name);
    List<Questionnaire> findAllByIsActive(boolean isActive);
}

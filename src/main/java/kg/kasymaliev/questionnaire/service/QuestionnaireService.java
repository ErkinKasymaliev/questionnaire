package kg.kasymaliev.questionnaire.service;

import kg.kasymaliev.questionnaire.entity.Questionnaire;

import java.util.List;
import java.util.Optional;

public interface QuestionnaireService {
    Optional<Questionnaire> getQuestionnaireById(Long id);
    List<Questionnaire> getByActivity(boolean isActive);
    List<Questionnaire> getAllQuestionnaire();
    Questionnaire addQuestionnaire(Questionnaire questionnaire);
    Questionnaire updateQuestionnaire(Questionnaire questionnaire);
    boolean deleteQuestionnaire(Long questionnaireId);
}

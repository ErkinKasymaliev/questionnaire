package kg.kasymaliev.questionnaire.service.impl;

import kg.kasymaliev.questionnaire.entity.Questionnaire;
import kg.kasymaliev.questionnaire.repository.QuestionnaireRepository;
import kg.kasymaliev.questionnaire.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionnaireServiceImpl implements QuestionnaireService {
    private final QuestionnaireRepository repository;

    @Override
    public Optional<Questionnaire> getQuestionnaireById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Questionnaire> getByActivity(boolean isActive) {
        return repository.findAllByIsActive(isActive);
    }

    @Override
    public List<Questionnaire> getAllQuestionnaire() {
        return null;
    }

    @Override
    public Questionnaire addQuestionnaire(Questionnaire questionnaire) {
        Questionnaire q = repository.findByName(questionnaire.getName()).orElse(null);
        if(q!=null)
            return null;
        return repository.save(questionnaire);
    }

    @Override
    public Questionnaire updateQuestionnaire(Questionnaire questionnaire) {
        Questionnaire q = repository.findById(questionnaire.getId()).orElse(null);
        if(q!=null)
            return repository.save(questionnaire);
        return null;
    }

    @Override
    public boolean deleteQuestionnaire(Long questionnaireId) {
        Questionnaire q = repository.findById(questionnaireId).orElse(null);
        if(q==null)
            return false;
        repository.delete(q);
        return true;
    }
}

package kg.kasymaliev.questionnaire.service.impl;

import kg.kasymaliev.questionnaire.entity.Questionnaire;
import kg.kasymaliev.questionnaire.entity.TakingSurvey;
import kg.kasymaliev.questionnaire.entity.User;
import kg.kasymaliev.questionnaire.repository.TakingSurveyRepository;
import kg.kasymaliev.questionnaire.service.TakingSurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TakingServiceImpl implements TakingSurveyService {
    private final TakingSurveyRepository takingSurveyRepository;

    @Override
    public Optional<TakingSurvey> getById(Long id) {
        return takingSurveyRepository.findById(id);
    }

    @Override
    public List<TakingSurvey> getByUser(User user) {
        return takingSurveyRepository.findAllByUser(user);
    }

    @Override
    public List<TakingSurvey> getByQuestionnaire(Questionnaire questionnaire) {
        return takingSurveyRepository.findAllByQuestionnaire(questionnaire);
    }

    @Override
    public List<TakingSurvey> getAllTakingSurveys() {
        return takingSurveyRepository.findAll();
    }

    @Override
    public List<TakingSurvey> getByCreatedDt(LocalDateTime createdDt) {
        return takingSurveyRepository.findAllByCreatedDt(createdDt);
    }

    @Override
    public List<TakingSurvey> getByEndDt(LocalDateTime endDt) {
        return takingSurveyRepository.findAllByEndDt(endDt);
    }

    @Override
    public TakingSurvey addTakingSurvey(TakingSurvey takingSurvey) {
        return takingSurveyRepository.save(takingSurvey);
    }

    @Override
    public TakingSurvey updateTakingSurvey(TakingSurvey takingSurvey) {
        TakingSurvey t = takingSurveyRepository.findById(takingSurvey.getId()).orElse(null);
        if(t==null)
            return null;
        return takingSurveyRepository.save(takingSurvey);
    }

    @Override
    public boolean deleteTakingSurvey(Long id) {
        TakingSurvey t = takingSurveyRepository.findById(id).orElse(null);
        if(t==null)
            return false;

        takingSurveyRepository.delete(t);
        return true;
    }
}

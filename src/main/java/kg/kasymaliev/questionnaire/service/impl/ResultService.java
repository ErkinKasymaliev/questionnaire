package kg.kasymaliev.questionnaire.service.impl;

import kg.kasymaliev.questionnaire.entity.Question;
import kg.kasymaliev.questionnaire.entity.Result;
import kg.kasymaliev.questionnaire.entity.TakingSurvey;
import kg.kasymaliev.questionnaire.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResultService implements kg.kasymaliev.questionnaire.service.ResultService {

    private final ResultRepository resultRepository;

    @Override
    public Optional<Result> getResultById(long id) {
        return resultRepository.findById(id);
    }

    @Override
    public List<Result> getResultsByTakingSurvey(TakingSurvey takingSurvey) {
        return resultRepository.findAllByTakingSurvey(takingSurvey);
    }

    @Override
    public List<Result> getResultsByTakingSurveyAndQuestion(TakingSurvey takingSurvey, Question question) {
        return resultRepository.findAllByTakingSurveyAndQuestion(takingSurvey, question);
    }

    @Override
    public void addResult(Result result) {
        resultRepository.save(result);
    }

    @Override
    public void addAllResults(List<Result> result) {
        resultRepository.saveAll(result);
    }

    @Override
    public Result updateResult(Result result) {
        Result res = resultRepository.findById(result.getId()).orElse(null);
        if(res!=null)
            resultRepository.save(result);
        return null;
    }

    @Override
    public Result deleteResult(Long id) {
        Result res = resultRepository.findById(id).orElse(null);
        if(res!=null)
            resultRepository.delete(res);
        return null;
    }
}

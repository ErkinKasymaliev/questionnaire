package kg.kasymaliev.questionnaire.mapper;

import kg.kasymaliev.questionnaire.dto.response.QuestionAnswerRsDto;
import kg.kasymaliev.questionnaire.dto.response.ResultRsDto;
import kg.kasymaliev.questionnaire.entity.Result;
import kg.kasymaliev.questionnaire.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ResultMapper {
    private final ResultService resultService;

    public ResultRsDto resultsToDto(List<Result> result) {
        String questionnaireName = result.get(0).getTakingSurvey().getQuestionnaire().getName();
        String createdDt = result.get(0).getTakingSurvey().getCreatedDt().toString();
        String endDt = result.get(0).getTakingSurvey().getEndDt().toString();
        String userName = result.get(0).getTakingSurvey().getUser().getLogin();

        List<QuestionAnswerRsDto> questionAnswerRsDto = result.stream()
                .map(r -> QuestionAnswerRsDto.builder()
                .questionName(r.getQuestion().getText())
                .answers(resultService.getResultsByTakingSurveyAndQuestion(r.getTakingSurvey(), r.getQuestion()).stream()
                        .map(a -> a.getAnswer().getText()).collect(Collectors.toList())
                )
                .build()).collect(Collectors.toList());

        return ResultRsDto.builder()
                .createDt(createdDt)
                .endDt(endDt)
                .questionnaireName(questionnaireName)
                .userName(userName)
                .answers(questionAnswerRsDto)
                .build();

    }
}

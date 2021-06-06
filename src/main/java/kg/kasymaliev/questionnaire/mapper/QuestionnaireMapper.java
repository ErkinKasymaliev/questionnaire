package kg.kasymaliev.questionnaire.mapper;

import kg.kasymaliev.questionnaire.dto.request.QuestionnaireDtoRq;
import kg.kasymaliev.questionnaire.entity.Questionnaire;
import kg.kasymaliev.questionnaire.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
public class QuestionnaireMapper {
    public final ModelMapper modelMapper;

    public Questionnaire rqDtoToEntity(QuestionnaireDtoRq questionnaireDtoRq) {
        Questionnaire questionnaire = modelMapper.map(questionnaireDtoRq, Questionnaire.class);
        questionnaire.setStartDt(DateUtil.stringToLocalDateTime(questionnaireDtoRq.getStartDt()));
        questionnaire.setEndDt(DateUtil.stringToLocalDateTime(questionnaireDtoRq.getEndDt()));
        return questionnaire;
    }

}

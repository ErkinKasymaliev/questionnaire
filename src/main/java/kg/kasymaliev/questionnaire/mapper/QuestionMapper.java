package kg.kasymaliev.questionnaire.mapper;

import kg.kasymaliev.questionnaire.dto.request.QuestionRqDto;
import kg.kasymaliev.questionnaire.entity.Question;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class QuestionMapper {
    private final ModelMapper modelMapper;

    public Question rqDtoToEntity(QuestionRqDto questionRqDto) {
        Question question = modelMapper.map(questionRqDto, Question.class);
        question.setAnswers(null);
        return question;
    }
}

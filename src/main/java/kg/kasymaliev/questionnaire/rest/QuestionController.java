package kg.kasymaliev.questionnaire.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kg.kasymaliev.questionnaire.dto.request.QuestionRqDto;
import kg.kasymaliev.questionnaire.entity.Answer;
import kg.kasymaliev.questionnaire.entity.Question;
import kg.kasymaliev.questionnaire.entity.Questionnaire;
import kg.kasymaliev.questionnaire.mapper.QuestionMapper;
import kg.kasymaliev.questionnaire.service.AnswerService;
import kg.kasymaliev.questionnaire.service.QuestionService;
import kg.kasymaliev.questionnaire.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/question")
@Api(tags = "Вопросы")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionnaireService questionnaireService;
    private final AnswerService answerService;

    private final QuestionMapper questionMapper;

    @GetMapping("/{id}")
    @PostMapping
    @ApiOperation("Запрос вопроса по id")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getQuestion(@PathVariable Long id) {
        return new ResponseEntity<>(questionService.getQuestion(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question id = " +
                        id + " is not found")),
                HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("Запрос всех вопросов")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllQuestions() {
        List<Question> list = questionService.getAllQuestions();
        if(list==null||list.isEmpty())
            return new ResponseEntity<>("There is not any questions", HttpStatus.NOT_FOUND);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/byQuestionnaire/{questionnaireId}")
    @ApiOperation("Запрос всех вопросов по id опроса")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllQuestionsByQuestionnaire(@PathVariable Long questionnaireId) {
        Questionnaire questionnaire = questionnaireService.getQuestionnaireById(questionnaireId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Questionnaire id = "
                                                                            + questionnaireId + " is not found"));
        List<Question> list = questionService.getByQuestionnaire(questionnaire);
        if(list==null||list.isEmpty())
            return new ResponseEntity<>("There is not any questions by questionnaire id= " + questionnaireId,
                    HttpStatus.NOT_FOUND);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Создание вопроса")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createQuestion(@RequestParam Long questionnaireId,
                                            @RequestBody QuestionRqDto questionRqDto) {
        Questionnaire questionnaire = questionnaireService.getQuestionnaireById(questionnaireId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Questionnaire id = "
                                + questionnaireId + " is not found"));

        Question question = questionMapper.rqDtoToEntity(questionRqDto);
        question.setQuestionnaire(questionnaire);

        Question q = questionService.addQuestion(question);
        Set<Answer> answers = questionRqDto.getAnswers().stream().map(a -> Answer
               .builder()
               .text(a.getText())
               .question(q)
               .build()).collect(Collectors.toSet());
        answerService.addAll(new ArrayList<>(answers));


        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(q.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(q);
    }

    @PutMapping("/{id}")
    @ApiOperation("Изменение вопроса вопроса")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody QuestionRqDto questionRqDto) {
        questionService.getQuestion(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question id = " +
                        id + " is not found"));

        Question questionMap = questionMapper.rqDtoToEntity(questionRqDto);
        questionMap.setId(id);
        Question question = questionService.updateQuestion(questionMap);
        /*TODO испровить на update answers*/
        /*Set<Answer> answers = questionRqDto.getAnswers().stream().map(a -> Answer
                .builder()
                .text(a.getText())
                .question(question)
                .build()).collect(Collectors.toSet());
        answerService.addAll(answers);*/

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location)
                .body(question);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление вопроса")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        Question question = questionService.getQuestion(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question id = " +
                        id + " is not found")
        );
        questionService.deleteQuestion(question);
        return new ResponseEntity<>("Question id = " + id + " is deleted", HttpStatus.OK);
    }
}

package kg.kasymaliev.questionnaire.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kg.kasymaliev.questionnaire.dto.request.QuestionnaireDtoRq;
import kg.kasymaliev.questionnaire.entity.Questionnaire;
import kg.kasymaliev.questionnaire.mapper.QuestionnaireMapper;
import kg.kasymaliev.questionnaire.service.QuestionnaireService;
import kg.kasymaliev.questionnaire.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.HttpURLConnection;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/questionnaire")
@Api(tags = "Опросы")
@RequiredArgsConstructor
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;
    private final QuestionnaireMapper questionnaireMapper;

    @GetMapping("/{id}")
    @ApiOperation("Запрос опроса по id")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<?> getQuestionnaire(@PathVariable Long id) {
        return new ResponseEntity<>(questionnaireService.getQuestionnaireById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Questionnaire id = " + id + " is not found")),
                HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("Запрос всех активных опросов")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<?> getAllActiveQuestionnaires() {
        List<Questionnaire> list = questionnaireService.getByActivity(true);
        if(list==null||list.isEmpty())
            return new ResponseEntity<>("There is not any questionnaire", HttpStatus.NOT_FOUND);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/isActive/{isActive}")
    @ApiOperation("Запрос всех опросов по признаку активности")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getAllQuestionnairesByActive(@PathVariable Boolean isActive) {
        List<Questionnaire> list = questionnaireService.getByActivity(isActive);
        String text = isActive ? "active" : "not active";
        if(list==null||list.isEmpty())
            return new ResponseEntity<>("There is not any " + text + " questionnaire", HttpStatus.NOT_FOUND);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Создание опроса")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> addQuestionnaire(@RequestBody QuestionnaireDtoRq questionnaireDtoRq) {
        Questionnaire questionnaire = questionnaireMapper.rqDtoToEntity(questionnaireDtoRq);
        questionnaire.setCreatedDt(LocalDateTime.now());
        Questionnaire q = questionnaireService.addQuestionnaire(questionnaire);
        if(q==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Questionnaire name = " + questionnaireDtoRq.getName() +
                    " is already exists");
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(q.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(q);
    }

    @PutMapping("/{id}")
    @ApiOperation("Изменение опроса")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateQuestionnaire(@PathVariable Long id, @RequestBody QuestionnaireDtoRq questionnaireDtoRq) {
        Questionnaire questionnaire = questionnaireService.getQuestionnaireById(id).orElse(null);
        if(questionnaire==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Questionnaire id = " + id +
                    " is not found");
        questionnaire.setDescription(questionnaireDtoRq.getDescription());
        questionnaire.setName(questionnaireDtoRq.getName());
        questionnaire.setIsActive(questionnaireDtoRq.isActive());
        questionnaire.setEndDt(DateUtil.stringToLocalDateTime(questionnaireDtoRq.getEndDt()));
        questionnaire.setStartDt(DateUtil.stringToLocalDateTime(questionnaireDtoRq.getStartDt()));
        Questionnaire q = questionnaireService.updateQuestionnaire(questionnaire);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(q.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(q);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление опроса")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteQuestionnaire(@PathVariable Long id) {
        questionnaireService.deleteQuestionnaire(id);
        return ResponseEntity.ok().build();
    }
}

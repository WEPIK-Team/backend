package wepik.backend.module.question.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wepik.backend.module.question.application.QuestionService;
import wepik.backend.module.question.dto.QuestionRequest;
import wepik.backend.module.question.dto.QuestionResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("questions")
public class QuestionController {

    private final QuestionService questionService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "질문 생성", description = "질문을 생성한다.")
    public QuestionResponse createQuestion(@RequestBody QuestionRequest questionRequest) {
        return questionService.save(questionRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    @Operation(summary = "질문 조회", description = "id 값에 해당하는 질문을 조회한다.")
    public QuestionResponse findQuestion(@PathVariable Long id) {
        return questionService.findQuestion(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    @Operation(summary = "질문 삭제", description = "id 값에 해당하는 질문을 삭제한다.")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.delete(id);
    }
}

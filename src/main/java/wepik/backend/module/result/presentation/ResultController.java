package wepik.backend.module.result.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wepik.backend.module.result.application.ResultService;
import wepik.backend.module.result.dto.ResultResponse;

@RestController
@RequiredArgsConstructor
@Tag(name = "Result", description = "결과 API")
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/result")
    @Operation(summary = "결과 조회", description = "UUID로 질문 결과를 조회한다.")
    public ResultResponse getResult(@RequestParam String senderId, @RequestParam String receiverId) {

        return resultService.loadResult(senderId, receiverId);
    }
}

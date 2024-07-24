package wepik.backend.module.analysis.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wepik.backend.module.analysis.application.AnalysisService;
import wepik.backend.module.analysis.dto.AnalysisResult;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("analysis")
@Tag(name = "Analysis", description = "결과 분석 api")
public class AnalysisController {

    private final AnalysisService analysisService;

    @GetMapping()
    public AnalysisResult getAnalysis(@RequestParam String senderId, @RequestParam String receiverId) throws JsonProcessingException {

        log.info("uuid : {}, {}", senderId, receiverId);
        return analysisService.getAnalysis(senderId, receiverId);
    }
}

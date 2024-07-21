package wepik.backend.module.analysis.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wepik.backend.module.analysis.dto.AnalysisResult;
import wepik.backend.module.analysis.dto.GptRequest;
import wepik.backend.module.analysis.dto.GptResponse;
import wepik.backend.module.result.application.ResultService;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.model}")
    private String gptModel;

    private final RestTemplate restTemplate;

    private final ResultService resultService;

    private final ObjectMapper objectMapper;

    public AnalysisResult getAnalysis(String senderId, String receiverId) throws JsonProcessingException {

        String prompt = generatePrompt(senderId, receiverId);
        GptRequest gptRequest = new GptRequest(gptModel, prompt);
        GptResponse gptResponse = restTemplate.postForObject(apiUrl, gptRequest, GptResponse.class);
        String content = gptResponse.getChoices().get(0).getMessage().getContent();

        return parseGptResponse(content);
    }

    private String generatePrompt(String senderId, String receiverId) throws JsonProcessingException {
        return "templateTitle 주제에 대해서 공통된 질문의 sender와 receiver 답변이 있어 이 두사람이 templateTitle 주제에 얼마나 잘 맞을지 score와 간단한 요약 description 두개만 json으로 나타내줘\n"
                + objectMapper.writeValueAsString(resultService.loadResult(senderId, receiverId));
    }

    private AnalysisResult parseGptResponse(String content) {
        try {
            Map<String, Object> responseMap = objectMapper.readValue(content, Map.class);
            int score = (Integer) responseMap.get("score");
            String description = (String) responseMap.get("description");
            return new AnalysisResult(score, description);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}

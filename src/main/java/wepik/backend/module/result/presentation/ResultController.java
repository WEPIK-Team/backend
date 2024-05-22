package wepik.backend.module.result.presentation;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wepik.backend.module.result.application.ResultService;
import wepik.backend.module.result.dto.ResultDto;

@RestController
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/result")
    public ResultDto getResult(@RequestParam String senderId, @RequestParam String receiverId) {

        return resultService.loadResult(senderId, receiverId);
    }
}

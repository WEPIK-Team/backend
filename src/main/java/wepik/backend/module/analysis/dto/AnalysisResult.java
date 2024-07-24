package wepik.backend.module.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AnalysisResult {

    private int score;
    private String description;
}

package umc9th_hackathon.daybreak.domain.mission.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeeklyMissionCreateRequest {

    @NotBlank
    private String category;

    @NotBlank
    @Size(max = 50)
    private String goal;
}

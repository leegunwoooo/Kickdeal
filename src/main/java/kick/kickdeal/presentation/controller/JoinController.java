package kick.kickdeal.presentation.controller;

import kick.kickdeal.presentation.dto.JoinDTO;
import kick.kickdeal.application.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinDTO joinDTO) {
        return joinService.joinprocess(joinDTO);
    }
}

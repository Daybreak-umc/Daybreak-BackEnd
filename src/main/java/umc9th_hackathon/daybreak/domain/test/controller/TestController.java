package umc9th_hackathon.daybreak.domain.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController implements TestControllerDocs{

    @Override
    @GetMapping("/healthcheck")
    public String healthcheck() {
        return "Controller is working!";
    }
}

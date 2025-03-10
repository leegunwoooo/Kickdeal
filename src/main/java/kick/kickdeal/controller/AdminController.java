package kick.kickdeal.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public Object ok() {
        return new Response("OK");
    }

    @Getter
    @Setter
    static class Response {
        private String message;

        public Response(String message) {
            this.message = message;
        }
    }
}

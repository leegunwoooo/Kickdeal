package kick.kickdeal.controller;

import jakarta.mail.MessagingException;

import kick.kickdeal.dto.CodeResponseDTO;
import kick.kickdeal.dto.MailDTO;
import kick.kickdeal.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @ResponseBody
    @PostMapping("/email")
    public CodeResponseDTO emailCheck(@RequestBody MailDTO mailDTO) throws MessagingException, UnsupportedEncodingException {
        String authCode = mailService.sendSimpleMessage(mailDTO.getEmail());
        return new CodeResponseDTO(authCode);
    }
}

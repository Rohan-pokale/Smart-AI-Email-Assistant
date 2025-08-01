package com.email.writer.Controller;


import com.email.writer.Model.EmailRequest;
import com.email.writer.Service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// /api/email/generate

@RestController
@RequestMapping("/api/email")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateEmail(@RequestBody EmailRequest emailRequest){
        String response=emailService.generateEmailReply(emailRequest);
        return ResponseEntity.ok(response);
    }
}
//material ui
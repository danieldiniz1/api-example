package br.com.sh.apiexample.controller.v1;

import br.com.sh.apiexample.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/email")
@Tag(name = "Email", description = "Operações relacionadas ao envio de e-mails")
public class EmailController {

    public static final Logger LOGGER = LoggerFactory.getLogger(EmailController.class);
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Operation(
        summary = "Enviar e-mail de teste",
        description = "Envia um e-mail de teste para um destinatário fixo.",
        tags = {"Email"},
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "E-mail enviado com sucesso"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Requisição inválida",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autenticado",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Não autorizado",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno ao enviar e-mail",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
        }
    )
    @GetMapping
    public ResponseEntity sendEmail() {
        try {
            emailService.sendEmail("daniel.diniz1@icloud.com", "Test Email", "This is a test email from the API example.");
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

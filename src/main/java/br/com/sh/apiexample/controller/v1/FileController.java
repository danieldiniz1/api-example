package br.com.sh.apiexample.controller.v1;

import br.com.sh.apiexample.exception.InvalidFileResourceException;
import br.com.sh.apiexample.facade.FileFacade;
import br.com.sh.apiexample.model.dto.ExceptionResponseDTO;
import br.com.sh.apiexample.model.dto.UploadFileResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileFacade fileFacade;

    public FileController(FileFacade fileFacade) {
        this.fileFacade = fileFacade;
    }

    @Operation(
            summary = "Upload de arquivo",
            description = "Realiza o upload de um arquivo e retorna informações do arquivo salvo.",
            tags = {"File"},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Arquivo salvo com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UploadFileResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Arquivo inválido ou erro de validação",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Requisição não autenticada",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Requisição não autorizada",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno ao processar o arquivo",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDTO.class))
                    )
            }
    )
    @PostMapping(value = "/upload", consumes = "multipart/form-data", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UploadFileResponseDTO> uploadFile(@RequestParam("file") MultipartFile file) {

        UploadFileResponseDTO response = fileFacade.uploadFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(
            summary = "Upload de múltiplos arquivos",
            description = "Realiza o upload de múltiplos arquivos e retorna informações dos arquivos salvos.",
            tags = {"File"},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Arquivos salvos com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UploadFileResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Arquivos inválidos ou erro de validação",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Requisição não autenticada",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Requisição não autorizada",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno ao processar os arquivos",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDTO.class))
                    )
            }
    )
    @PostMapping(value = "/upload/all", consumes = "multipart/form-data", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<UploadFileResponseDTO>> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        logger.info("Uploading all files");
        List<UploadFileResponseDTO> response = Arrays.stream(files)
                .map(fileFacade::uploadFile)
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Download de arquivo",
            description = "Realiza o download de um arquivo pelo nome informado.",
            tags = {"File"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Arquivo encontrado e retornado com sucesso",
                            content = @Content(mediaType = "application/octet-stream")
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Nome do arquivo inválido",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Requisição não autenticada",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Requisição não autorizada",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Arquivo não encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno ao processar o download",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDTO.class))
                    )
            }
    )
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName, HttpServletRequest request) {
        validateFileName(fileName);
        Resource resource = fileFacade.downloadFile(fileName, request);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            logger.error("Could not determine file type for: {}", fileName, e);
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
            logger.warn("Could not determine content type for file: {}, defaulting to application/octet-stream", fileName);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + contentType + resource.getFilename() + "\"")
                .body(resource);
    }

    private void validateFileName(String fileName) {
        if (Objects.isNull(fileName) || fileName.isEmpty()) {
            logger.error("File name is empty or null");
            throw new InvalidFileResourceException("File name cannot be empty or null");
        }
    }

}

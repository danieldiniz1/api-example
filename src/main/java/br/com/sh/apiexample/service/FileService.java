package br.com.sh.apiexample.service;

import br.com.sh.apiexample.model.dto.UploadFileResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    UploadFileResponseDTO uploadFile(MultipartFile file);
}

package br.com.sh.apiexample.service;

import br.com.sh.apiexample.model.dto.UploadFileResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    UploadFileResponseDTO uploadFile(MultipartFile file);

    Resource downloadFile(@RequestParam String fileName);
}

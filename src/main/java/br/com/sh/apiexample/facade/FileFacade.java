package br.com.sh.apiexample.facade;

import br.com.sh.apiexample.model.dto.UploadFileResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileFacade {

    UploadFileResponseDTO uploadFile(MultipartFile file) ;

    Resource downloadFile(String fileName, HttpServletRequest request);
}

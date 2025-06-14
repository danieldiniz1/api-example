package br.com.sh.apiexample.facade.impl;

import br.com.sh.apiexample.facade.FileFacade;
import br.com.sh.apiexample.model.dto.UploadFileResponseDTO;
import br.com.sh.apiexample.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class DefaultFileFacade implements FileFacade {

    private static final Logger logger = LoggerFactory.getLogger(DefaultFileFacade.class);

    private final FileService fileService;

    public DefaultFileFacade(FileService fileService) {
        this.fileService = fileService;
    }


    @Override
    public UploadFileResponseDTO uploadFile(MultipartFile file) {
        logger.info("Uploading file: {}", file.getOriginalFilename());
        return fileService.uploadFile(file);
    }
}

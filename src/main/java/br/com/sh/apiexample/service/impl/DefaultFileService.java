package br.com.sh.apiexample.service.impl;

import br.com.sh.apiexample.config.storage.FileStorageConfig;
import br.com.sh.apiexample.exception.FileResourceNotFoundException;
import br.com.sh.apiexample.exception.FileStorageException;
import br.com.sh.apiexample.exception.InvalidFileResourceException;
import br.com.sh.apiexample.model.dto.UploadFileResponseDTO;
import br.com.sh.apiexample.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class DefaultFileService implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultFileService.class);
    private final Path filePath;

    public DefaultFileService(FileStorageConfig fileStorageConfig) {
        this.filePath = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath()
                .normalize();

        createDirectory(filePath);
    }

    @Override
    public UploadFileResponseDTO uploadFile(MultipartFile file) {
        var fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            validateFileName(fileName);
            Path targetLocation = filePath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            logger.info("File {} uploaded successfully to {}", fileName, targetLocation);
            return new UploadFileResponseDTO(fileName, getUrlDownloadResponse(fileName), file.getContentType(), file.getSize());
        } catch (IOException e) {
            logger.error("Could not store file: {}", fileName, e);
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
    }

    @Override
    public Resource downloadFile(String fileName) {
        try {
            Path targetFileLocation = filePath.resolve(fileName).normalize();
            UrlResource resource = new UrlResource(targetFileLocation.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new FileResourceNotFoundException("Could not find file " + fileName);
            }
        } catch (IOException e) {
            logger.error("Could not read file: {}", fileName, e);
            throw new FileResourceNotFoundException("Could not read file " + fileName + ". Please try again!", e);
        }
    }

    private void validateFileName(String fileName) {
        if (fileName.contains("..")) {
            logger.error("Invalid file name: {}", fileName);
            throw new InvalidFileResourceException("Sorry! Filename contains invalid path sequence " + fileName);
        }

        if (fileName.isEmpty()) {
            logger.error("File name is empty");
            throw new InvalidFileResourceException("File name cannot be empty");

        }
    }

    private void createDirectory(Path path) {
        try {
            Files.createDirectories(filePath);
            logger.info("Upload directory created at: {}", path);
        } catch (Exception e) {
            logger.error("Could not create upload directory: {}", path, e);
            throw new FileStorageException("Could not create upload directory", e);
        }
    }

    private static String getUrlDownloadResponse(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/file/download")
                .path("?fileName=" + fileName)
                .build().toUriString();
    }
}

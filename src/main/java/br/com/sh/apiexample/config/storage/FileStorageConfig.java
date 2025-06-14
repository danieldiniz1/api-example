package br.com.sh.apiexample.config.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageConfig {

    @Value("${file.upload.dir}")
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

}

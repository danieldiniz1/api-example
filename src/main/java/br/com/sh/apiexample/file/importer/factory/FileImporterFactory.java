package br.com.sh.apiexample.file.importer.factory;

import br.com.sh.apiexample.exception.InvalidFileResourceException;
import br.com.sh.apiexample.file.importer.FileImporter;
import br.com.sh.apiexample.file.importer.impl.CsvImporter;
import br.com.sh.apiexample.file.importer.impl.XlsxImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FileImporterFactory {

    private static final Logger log = LoggerFactory.getLogger(FileImporterFactory.class);
    private final ApplicationContext applicationContext;

    public FileImporterFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public FileImporter getFileImporter(String fileName) throws  Exception {
        validateName(fileName);
        if (fileName.endsWith(".xlsx")) return applicationContext.getBean(XlsxImporter.class);
        if (fileName.endsWith(".csv")) return applicationContext.getBean(CsvImporter.class);
        throw  new InvalidFileResourceException("Invalid file format: " + fileName.substring(fileName.lastIndexOf('.')));
    }

    private static void validateName(String fileName) {
        if (Objects.isNull(fileName) ||fileName.isBlank() ) {
            log.error("File name cannot be null or blank");
            throw new InvalidFileResourceException("File name cannot be null or blank");
        }
    }
}

package br.com.sh.apiexample.file.exporter.factory;

import br.com.sh.apiexample.exception.InvalidFileResourceException;
import br.com.sh.apiexample.file.exporter.FileExporter;
import br.com.sh.apiexample.file.exporter.impl.CsvExporter;
import br.com.sh.apiexample.file.exporter.impl.XlsxExporter;
import br.com.sh.apiexample.util.constants.MediaTypeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FileExporterFactory {

    @Value("${file.accepted.xlsx}")
    private String XLSX_CONTENT_TYPE;

    @Value("${file.accepted.csv}")
    private String CSV_CONTENT_TYPE;


    private static final Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);
    private final ApplicationContext applicationContext;

    public FileExporterFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public FileExporter getFileExporter(String fileName) {
        validateName(fileName);
        if (fileName.equalsIgnoreCase(MediaTypeConstants.APPLICATION_XLSX)) return applicationContext.getBean(XlsxExporter.class);
        if (fileName.equalsIgnoreCase(MediaTypeConstants.TEXT_CSV)) return applicationContext.getBean(CsvExporter.class);
        throw new InvalidFileResourceException("Invalid file format:");
    }

    private static void validateName(String fileName) {
        if (Objects.isNull(fileName) || fileName.isBlank()) {
            logger.error("File name cannot be null or blank");
            throw new InvalidFileResourceException("File name cannot be null or blank");
        }
    }
}

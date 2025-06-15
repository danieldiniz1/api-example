package br.com.sh.apiexample.file.exporter.impl;

import br.com.sh.apiexample.exception.WriterFileException;
import br.com.sh.apiexample.file.exporter.FileExporter;
import br.com.sh.apiexample.model.UserModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvExporter implements FileExporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvExporter.class);

    @Override
    public Resource export(List<UserModel> userList) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        CSVFormat format = CSVFormat.DEFAULT
                .builder()
                .setSkipHeaderRecord(false)
                .setHeader("ID", "firstname", "lastname", "cpf", "email").get();

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, format)) {
            userList.forEach(user -> {
                try {
                    csvPrinter.printRecord(user.getId(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getCpf(),
                            user.getEmail());
                } catch (IOException e) {
                    LOGGER.error("Error while writing user data to CSV", e);
                }
            });
        } catch (IOException e) {
            LOGGER.error("Error while exporting CSV file", e);
            throw new WriterFileException("Error while exporting CSV file", e);
        }

        return new ByteArrayResource(outputStream.toByteArray());
    }
}

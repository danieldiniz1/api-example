package br.com.sh.apiexample.file.importer.impl;

import br.com.sh.apiexample.exception.InvalidFileResourceException;
import br.com.sh.apiexample.file.importer.FileImporter;
import br.com.sh.apiexample.model.form.AddressForm;
import br.com.sh.apiexample.model.form.ContactForm;
import br.com.sh.apiexample.model.form.UserForm;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvImporter implements FileImporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvImporter.class);

    @Override
    public List<UserForm> importFileInputStreamToUserFormList(String fileName, String contentType, InputStream fileInputStream)  {
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .setDelimiter(',')
                .get();

        try {
            return parseRecordsToUserForms(format.parse(new InputStreamReader(fileInputStream)));
        } catch (IOException e) {
            throw new InvalidFileResourceException("Error reading CSV file: " + e.getMessage(), e);
        }

    }

    private List<UserForm> parseRecordsToUserForms(Iterable<CSVRecord> records) {
        List<UserForm> userForms = new ArrayList<>();
        records.forEach(line -> {
            LOGGER.info("Processing line: {}", line);

            UserForm userForm = new UserForm(line.get("firstName"), line.get("lastName"), line.get("email"), line.get("cpf"),
                    new AddressForm(line.get("address.street"), line.get("address.number"), line.get("address.city"), line.get("address.state"), line.get("address.country"), line.get("address.zipCode")),
                    new ContactForm(line.get("contact.phone"), line.get("contact.mobile"), line.get("contact.emailSecundary")));
            LOGGER.info("Processing line: {} and result in form {}", line, userForm);

            userForms.add(userForm);
        });
        return userForms;
    }
}

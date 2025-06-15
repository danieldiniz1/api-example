package br.com.sh.apiexample.file.importer.impl;

import br.com.sh.apiexample.exception.InvalidFileResourceException;
import br.com.sh.apiexample.file.importer.FileImporter;
import br.com.sh.apiexample.model.form.AddressForm;
import br.com.sh.apiexample.model.form.ContactForm;
import br.com.sh.apiexample.model.form.UserForm;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class XlsxImporter implements FileImporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(XlsxImporter.class);

    @Override
    public List<UserForm> importFileInputStreamToUserFormList(String fileName, String contentType, InputStream fileInputStream)  {

        try (XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) rowIterator.next();
            return parseRowsToUserForms(rowIterator);

        } catch (IllegalStateException e) {
            LOGGER.error("Cannot read line in file: {}", fileName, e);
            throw new InvalidFileResourceException("Error processing XLSX file: " + e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error("Cannot read file: {}", fileName, e);
            throw new InvalidFileResourceException("Error reading XLSX file: " + e.getMessage(), e);
        }
    }

    private List<UserForm> parseRowsToUserForms(Iterator<Row> rowIterator) {
        List<UserForm> userForms = new ArrayList<>();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (isRowValid(row)) {
                UserForm userForm = parseRowToUserForm(row);
                userForms.add(userForm);
            }
        }
        return userForms;
    }

    private UserForm parseRowToUserForm(Row row) {
        String firstName = row.getCell(0).getStringCellValue();
        String lastName = row.getCell(1).getStringCellValue();
        String email = row.getCell(2).getStringCellValue();
        String cpf = row.getCell(3).getNumericCellValue() == 0 ? null : String.valueOf((long) row.getCell(3).getNumericCellValue());

        AddressForm addressForm = new AddressForm(
                row.getCell(4).getStringCellValue(),
                row.getCell(5).getNumericCellValue() == 0 ? null : String.valueOf(row.getCell(5).getNumericCellValue()),
                row.getCell(6).getStringCellValue(),
                row.getCell(7).getStringCellValue(),
                row.getCell(8).getStringCellValue(),
                row.getCell(9).getNumericCellValue() == 0 ? null : String.valueOf((long) row.getCell(9).getNumericCellValue())
        );

        ContactForm contactForm = new ContactForm(
                row.getCell(10).getStringCellValue(),
                row.getCell(11).getStringCellValue(),
                row.getCell(12).getStringCellValue()
        );

        return new UserForm(firstName, lastName, email, cpf, addressForm, contactForm);
    }

    private boolean isRowValid(Row row) {
        return row.getCell(0) != null && row.getCell(0).getCellType() != CellType.BLANK;
    }
}

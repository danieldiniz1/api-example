package br.com.sh.apiexample.file.exporter.impl;

import br.com.sh.apiexample.exception.WriterFileException;
import br.com.sh.apiexample.file.exporter.FileExporter;
import br.com.sh.apiexample.model.UserModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class XlsxExporter implements FileExporter {

    private static final Logger logger = LoggerFactory.getLogger(XlsxExporter.class);

    @Override
    public Resource export(List<UserModel> userList) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            var sheet = workbook.createSheet("Users");
            var headerRow = sheet.createRow(0);
            String[] headers = {"ID", "First Name", "Last Name", "CPF", "Email"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderCellStyle(workbook)); // Set default style
            }

            for (int i = 0; i < userList.size(); i++) {
                UserModel user = userList.get(i);
                var row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getFirstName());
                row.createCell(2).setCellValue(user.getLastName());
                row.createCell(3).setCellValue(user.getCpf());
                row.createCell(4).setCellValue(user.getEmail());
            }

            // Auto-size columns for better readability
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return new ByteArrayResource(outputStream.toByteArray());
        } catch (IOException e) {
            logger.error("Error while exporting XLSX file", e);
            throw new WriterFileException("Error while exporting CSV file", e);
        }

    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }


}

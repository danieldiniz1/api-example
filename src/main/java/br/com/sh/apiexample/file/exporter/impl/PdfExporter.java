package br.com.sh.apiexample.file.exporter.impl;

import br.com.sh.apiexample.exception.PdfTemplateNotFoundException;
import br.com.sh.apiexample.file.exporter.FileExporter;
import br.com.sh.apiexample.model.UserModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class PdfExporter implements FileExporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfExporter.class);
    private static final String PDF_RESOURCE = "/templates/RelatorioPessoaTemplate.jrxml";
    private static final String PDF_RESOURCE_NEW = "/templates/person.jrxml";

    @Override
    public Resource export(List<UserModel> userList) {
        try (InputStream inputStream = getClass().getResourceAsStream(PDF_RESOURCE); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            if (Objects.isNull(inputStream)) {
                LOGGER.error("Template file not found: {}", PDF_RESOURCE);
                throw new PdfTemplateNotFoundException("Template file not found: " + PDF_RESOURCE);
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(userList);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, getParamiters(), dataSource);

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            LOGGER.info("PDF export successful for {} users", userList.size());
            return new ByteArrayResource(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            LOGGER.error("PDF export failed for {} users", userList.size(), e);
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> getParamiters() {
        Map<String, Object> parameters = new HashMap<>(Map.of(
                "reportTitle", "User Report",
                "createdBy", "API Example"
        ));
        parameters.put("reportTitle", "User Report");
        return parameters;
    }
}

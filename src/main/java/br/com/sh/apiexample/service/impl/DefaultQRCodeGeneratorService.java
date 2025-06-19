package br.com.sh.apiexample.service.impl;

import br.com.sh.apiexample.service.QRCodeGeneratorService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class DefaultQRCodeGeneratorService implements QRCodeGeneratorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultQRCodeGeneratorService.class);

    @Override
    public InputStream generateQRCode(String url, int width, int height) throws Exception {
        LOGGER.info("Generating QR code for URL: {}, Width: {}, Height: {}", url, width, height);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);

        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}

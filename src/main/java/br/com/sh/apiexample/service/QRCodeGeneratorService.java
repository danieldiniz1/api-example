package br.com.sh.apiexample.service;

import java.io.InputStream;

public interface QRCodeGeneratorService {

    InputStream generateQRCode(String url, int width, int height) throws Exception;
}

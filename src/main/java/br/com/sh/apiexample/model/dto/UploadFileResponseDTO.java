package br.com.sh.apiexample.model.dto;

public record UploadFileResponseDTO(
        String fileName,
        String fileDownloadUri,
        String fileType,
        long size) {


}

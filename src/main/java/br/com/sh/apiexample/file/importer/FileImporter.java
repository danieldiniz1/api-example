package br.com.sh.apiexample.file.importer;

import br.com.sh.apiexample.model.form.UserForm;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {

    List<UserForm> importFileInputStreamToUserFormList(String fileName, String contentType, InputStream fileInputStream) throws Exception;
}

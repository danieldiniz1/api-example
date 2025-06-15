package br.com.sh.apiexample.file.exporter;

import br.com.sh.apiexample.model.UserModel;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface FileExporter {

    Resource export(List<UserModel> userList) throws IOException;
}

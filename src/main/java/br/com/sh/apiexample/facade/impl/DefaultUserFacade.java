package br.com.sh.apiexample.facade.impl;

import br.com.sh.apiexample.exception.InvalidFileResourceException;
import br.com.sh.apiexample.facade.UserFacade;
import br.com.sh.apiexample.facade.mapper.converter.UserConverter;
import br.com.sh.apiexample.file.exporter.FileExporter;
import br.com.sh.apiexample.file.exporter.factory.FileExporterFactory;
import br.com.sh.apiexample.file.importer.FileImporter;
import br.com.sh.apiexample.file.importer.factory.FileImporterFactory;
import br.com.sh.apiexample.model.UserModel;
import br.com.sh.apiexample.model.dto.UserDto;
import br.com.sh.apiexample.model.form.UserForm;
import br.com.sh.apiexample.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DefaultUserFacade implements UserFacade {

    private static Logger logger = LoggerFactory.getLogger(DefaultUserFacade.class);
    private final UserService userService;

    private final FileImporterFactory fileImporterFactory;
    private final FileExporterFactory fileExporterFactory;

    private final UserConverter userConverter;

    public DefaultUserFacade(UserService userService, FileImporterFactory fileImporterFactory, FileExporterFactory fileExporterFactory, UserConverter userConverter) {
        this.userService = userService;
        this.fileImporterFactory = fileImporterFactory;
        this.fileExporterFactory = fileExporterFactory;
        this.userConverter = userConverter;
    }

    @Override
    public UserDto createUser(UserForm form) {
        UserModel user = userConverter.convertToModel(form);
        logger.info("Creating user with email: {}", user.getEmail());
        return userConverter.convertToDto(userService.save(user));
    }

    @Override
    public UserDto getUser(String cpf) {
        return userConverter.convertToDto(userService.findBycpf(cpf));
    }

    @Override
    public void updateUser(String email, String cpf) {
        userService.updateUser(email, cpf);
    }

    @Override
    public void deleteUser(String cpf) {
        userService.deleteUser(cpf);
    }

    @Override
    public Page<UserDto> getAllUsers(PageRequest pageRequest) {
        return userService.findAllUsersPaginated(pageRequest).map(userConverter::convertToDto);
//        return userService.findallUsers().stream().map(userConverter::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> createUsersInBatch(MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvalidFileResourceException("File is empty: " + file.getName());
        }

        List<UserForm> userForms;
        String originalFilename = Optional.ofNullable(file.getOriginalFilename()).orElseThrow(() -> new InvalidFileResourceException("File name cannot be null or empty"));
        FileImporter fileImporter = fileImporterFactory.getFileImporter(originalFilename);
        try (InputStream inputStream = file.getInputStream()) {
            userForms = fileImporter.importFileInputStreamToUserFormList(originalFilename, file.getContentType(),inputStream);
        } catch (IOException e) {
            throw new InvalidFileResourceException("Cannot read file: " + file.getName(), e);
        } catch (Exception e) {
            throw new InvalidFileResourceException("Cannot get file importer for file: " + file.getName(), e);
        }

        return userService.saveAllUsers(
                        userForms
                                .stream()
                                .map(userConverter::convertToModel)
                                .collect(Collectors.toList()))
                .stream()
                .map(userConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Resource downloadFileData(String contentType, PageRequest pageRequest) {
        FileExporter fileExporter = fileExporterFactory.getFileExporter(contentType);
        List<UserModel> userListFilter = userService.findAllUsersPaginated(pageRequest).getContent();
        try {
            return fileExporter.export(userListFilter);
        }catch (IOException e) {
            logger.error("Error exporting file data: {}", e.getMessage());
            throw new InvalidFileResourceException("Error exporting file data", e);
        }
    }

}

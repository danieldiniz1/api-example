package br.com.sh.apiexample.facade.mapper.converter;

import br.com.sh.apiexample.model.UserModel;
import br.com.sh.apiexample.model.dto.UserDto;
import br.com.sh.apiexample.model.form.UserForm;

public interface UserConverter {

    /**
     * Converts a UserDto to a UserModel.
     * version 1.0
     *
     * @param userForm the UserForm to convert
     * @return the converted UserModel
     */
    UserModel convertToModel(UserForm userForm);

    /**
     * Converts a UserModel to a UserDto.
     * version 1.0
     *
     * @param userModel the UserModel to convert
     * @return the converted UserDto
     */
    UserDto convertToDto(UserModel userModel);
}

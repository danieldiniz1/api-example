package br.com.sh.apiexample.facade.impl;

import br.com.sh.apiexample.facade.UserFacade;
import br.com.sh.apiexample.model.AddressModel;
import br.com.sh.apiexample.model.ContactModel;
import br.com.sh.apiexample.model.UserModel;
import br.com.sh.apiexample.model.dto.UserDto;
import br.com.sh.apiexample.model.form.AddressForm;
import br.com.sh.apiexample.model.form.ContactForm;
import br.com.sh.apiexample.model.form.UserForm;
import br.com.sh.apiexample.populator.Populator;
import br.com.sh.apiexample.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserFacade implements UserFacade {

    private static Logger logger = LogManager.getLogger(DefaultUserFacade.class);
    private final UserService userService;

    private final Populator<UserForm,UserModel> userModelPopulator;
    private final Populator<AddressForm, AddressModel> addressModelPopulator;
    private final Populator<ContactForm, ContactModel> contactModelPopulator;

    private final Populator<UserModel, UserDto> userRevertDtoPopulator;

    public DefaultUserFacade(UserService userService, Populator<UserForm, UserModel> userModelPopulator, Populator<AddressForm, AddressModel> addressModelPopulator, Populator<ContactForm, ContactModel> contactModelPopulator, Populator<UserModel, UserDto> userRevertDtoPopulator) {
        this.userService = userService;
        this.userModelPopulator = userModelPopulator;
        this.addressModelPopulator = addressModelPopulator;
        this.contactModelPopulator = contactModelPopulator;
        this.userRevertDtoPopulator = userRevertDtoPopulator;
    }

    @Override
    public UserDto createUser(UserForm form) {
        UserModel user = convertUserFormToModel(form);
        logger.info("Creating user with email: {}", user.getEmail());
        logger.info("Creating user with name: {}", user.getFirstName());
        logger.info("User address: {}", user.getAddress());
        logger.info("User contact: {}", user.getContact());
        userService.save(user);
        return userRevertDtoPopulator.populate(user);
    }

    private UserModel convertUserFormToModel(UserForm form) {
        UserModel userModel = userModelPopulator.populate(form);
        userModel.setAddress(addressModelPopulator.populate(form.address()));
        userModel.setContact(contactModelPopulator.populate(form.contact()));
        return userModel;
    }
}

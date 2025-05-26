package br.com.sh.apiexample.facade.mapper.converter.impl;

import br.com.sh.apiexample.facade.mapper.converter.UserConverter;
import br.com.sh.apiexample.facade.impl.DefaultUserFacade;
import br.com.sh.apiexample.facade.mapper.populator.Populator;
import br.com.sh.apiexample.model.AddressModel;
import br.com.sh.apiexample.model.ContactModel;
import br.com.sh.apiexample.model.UserModel;
import br.com.sh.apiexample.model.dto.UserDto;
import br.com.sh.apiexample.model.form.AddressForm;
import br.com.sh.apiexample.model.form.ContactForm;
import br.com.sh.apiexample.model.form.UserForm;
import br.com.sh.apiexample.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DefaultUserConverter implements UserConverter {

    private static Logger logger = LoggerFactory.getLogger(DefaultUserFacade.class);

    private final AddressService addressService;

    private final Populator<UserForm,UserModel> userModelPopulator;
    private final Populator<AddressForm, AddressModel> addressModelPopulator;
    private final Populator<ContactForm, ContactModel> contactModelPopulator;

    private final Populator<UserModel, UserDto> userRevertDtoPopulator;

    public DefaultUserConverter(AddressService addressService, Populator<UserForm, UserModel> userModelPopulator,
                                Populator<AddressForm, AddressModel> addressModelPopulator,
                                Populator<ContactForm, ContactModel> contactModelPopulator,
                                Populator<UserModel, UserDto> userRevertDtoPopulator) {
        this.addressService = addressService;
        this.userModelPopulator = userModelPopulator;
        this.addressModelPopulator = addressModelPopulator;
        this.contactModelPopulator = contactModelPopulator;
        this.userRevertDtoPopulator = userRevertDtoPopulator;
    }


    @Override
    public UserModel convertToModel(UserForm userForm) {
        Objects.requireNonNull(userForm);
        Objects.requireNonNull(userForm.address());
        Objects.requireNonNull(userForm.contact());
        UserModel userModel = userModelPopulator.populate(userForm);
        addressService.findByKeys(userForm.address())
                .ifPresentOrElse(
                        addressModel -> {
                            logger.info("Address found for user: {}", userModel.getEmail());
                            userModel.setAddress(addressModel);
                        },
                        () -> {
                            logger.warn("Address not found for user: {}", userModel.getEmail());
                            userModel.setAddress(addressModelPopulator.populate(userForm.address()));
                        }
                );
        userModel.setContact(contactModelPopulator.populate(userForm.contact()));
        return userModel;
    }

    @Override
    public UserDto convertToDto(UserModel userModel) {
        return userRevertDtoPopulator.populate(userModel);
    }
}

package br.com.sh.apiexample.populator.impl;

import br.com.sh.apiexample.model.UserModel;
import br.com.sh.apiexample.model.form.UserForm;
import br.com.sh.apiexample.populator.Populator;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserPopulator implements Populator<UserForm, UserModel> {

    @Deprecated(since = "1.0", forRemoval = true)
    @Override
    public UserModel populate(UserForm source, UserModel target) {
        target.setFirstName(source.firstName());
        target.setLastName(source.lastName());
        target.setEmail(source.email());

        return target;
    }

    @Override
    public UserModel populate(UserForm source) {
        return new UserModel.Builder()
                .firstName(source.firstName())
                .lastName(source.lastName())
                .email(source.email())
                .cpf(source.cpf())
                .active(true)
                .build();
    }

}

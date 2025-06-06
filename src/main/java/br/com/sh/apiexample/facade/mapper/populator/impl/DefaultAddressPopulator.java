package br.com.sh.apiexample.facade.mapper.populator.impl;

import br.com.sh.apiexample.model.AddressModel;
import br.com.sh.apiexample.model.form.AddressForm;
import br.com.sh.apiexample.facade.mapper.populator.Populator;
import org.springframework.stereotype.Component;

@Component
public class DefaultAddressPopulator implements Populator<AddressForm, AddressModel> {
    @Deprecated(since = "1.0", forRemoval = true)
    @Override
    public AddressModel populate(AddressForm source, AddressModel target) {
        target.setStreet(source.street());
        target.setNumber(source.number());
        target.setCity(source.city());
        target.setState(source.state());
        target.setCountry(source.country());
        target.setZipCode(source.zipCode());
        return target;
    }

    @Override
    public AddressModel populate(AddressForm source) {
        return new AddressModel.Builder()
                .street(source.street())
                .number(source.number())
                .city(source.city())
                .state(source.state())
                .country(source.country())
                .zipCode(source.zipCode())
                .build();
    }

}

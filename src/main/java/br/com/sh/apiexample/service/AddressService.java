package br.com.sh.apiexample.service;

import br.com.sh.apiexample.model.AddressModel;
import br.com.sh.apiexample.model.form.AddressForm;

import java.util.Optional;

public interface AddressService {

    Boolean existsByKeys(AddressForm addressForm);
    Optional<AddressModel> findByKeys(AddressForm addressForm);
}

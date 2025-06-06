package br.com.sh.apiexample.service.impl;

import br.com.sh.apiexample.model.AddressModel;
import br.com.sh.apiexample.model.form.AddressForm;
import br.com.sh.apiexample.repository.AddressRepository;
import br.com.sh.apiexample.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultAddressService implements AddressService {

    private final AddressRepository addressRepository;

    public DefaultAddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Boolean existsByKeys(AddressForm form) {
        return addressRepository.existsByKeys(form.street(), form.number(), form.city(), form.state(), form.country(), form.zipCode());
    }

    @Override
    public Optional<AddressModel> findByKeys(AddressForm form) {
        return addressRepository.findByKeys(form.street(), form.number(), form.city(), form.state(), form.country(), form.zipCode());
//        return optionalAddressModel.orElseThrow(() -> new RuntimeException("Address not found"));
    }
}

package br.com.sh.apiexample.service.impl;

import br.com.sh.apiexample.model.AddressModel;
import br.com.sh.apiexample.model.form.AddressForm;
import br.com.sh.apiexample.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultAddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private DefaultAddressService defaultAddressService;

    private AddressForm addressForm;

    @BeforeEach
    void setUp() {
        addressForm = new AddressForm(
            "Rua A",
            "123",
            "Cidade",
            "Estado",
            "País",
            "00000-000"
        );
    }

    @Test
    void existsByKeys_shouldReturnTrue_whenAddressExists() {
        when(addressRepository.existsByKeys(
                anyString(), anyString(), anyString(), anyString(), anyString(), anyString()
        )).thenReturn(true);

        Boolean exists = defaultAddressService.existsByKeys(addressForm);

        assertTrue(exists);
        verify(addressRepository).existsByKeys(anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void existsByKeys_shouldReturnFalse_whenAddressDoesNotExist() {
        when(addressRepository.existsByKeys(
                anyString(), anyString(), anyString(), anyString(), anyString(), anyString()
        )).thenReturn(false);

        Boolean exists = defaultAddressService.existsByKeys(addressForm);

        assertFalse(exists);
        verify(addressRepository).existsByKeys(anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void findByKeys_shouldReturnAddressModel_whenFound() {
        AddressModel addressModel = new AddressModel();
        when(addressRepository.findByKeys(
                anyString(), anyString(), anyString(), anyString(), anyString(), anyString()
        )).thenReturn(Optional.of(addressModel));

        Optional<AddressModel> result = defaultAddressService.findByKeys(addressForm);

        assertTrue(result.isPresent());
        assertEquals(addressModel, result.get());
        verify(addressRepository).findByKeys(anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void findByKeys_shouldReturnEmpty_whenNotFound() {
        when(addressRepository.findByKeys(
                anyString(), anyString(), anyString(), anyString(), anyString(), anyString()
        )).thenReturn(Optional.empty());

        Optional<AddressModel> result = defaultAddressService.findByKeys(addressForm);

        assertFalse(result.isPresent());
        verify(addressRepository).findByKeys(anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
    }
}

package br.com.sh.apiexample.model.form;

public record AddressForm(String street,
                          String number,
                          String city,
                          String state,
                          String country,
                          String zipCode) {
}

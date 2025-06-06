package br.com.sh.apiexample.model.form;

public record UserForm( String firstName,
                        String lastName,
                        String email,
                        String cpf,
                        AddressForm address,
                        ContactForm contact) {
}

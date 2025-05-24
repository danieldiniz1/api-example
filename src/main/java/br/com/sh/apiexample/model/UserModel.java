package br.com.sh.apiexample.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;


@Entity
@Table(name = "tb_user")

public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 80)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressModel address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private ContactModel contact;

    public static class Builder {
        private String firstName;
        private String lastName;
        private String cpf;
        private String email;
        private AddressModel address;
        private ContactModel contact;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder cpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder address(AddressModel address) {
            this.address = address;
            return this;
        }

        public Builder contact(ContactModel contact) {
            this.contact = contact;
            return this;
        }

        public UserModel build() {
            UserModel userModel = new UserModel();
            userModel.firstName = this.firstName;
            userModel.lastName = this.lastName;
            userModel.cpf = this.cpf;
            userModel.email = this.email;
            userModel.address = this.address;
            userModel.contact = this.contact;

            return userModel;
        }
    }
    public long getId() {return id;}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public ContactModel getContact() {
        return contact;
    }

    public void setContact(ContactModel contact) {
        this.contact = contact;
    }
}

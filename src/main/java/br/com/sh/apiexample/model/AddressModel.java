package br.com.sh.apiexample.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_address", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"street", "number","zipCode"})
},
        indexes = {
                @Index(name = "idx_general", columnList = "street, city, state"),
                @Index(name = "idx_street", columnList = "street"),
                @Index(name = "idx_city", columnList = "city"),
                @Index(name = "idx_state", columnList = "state"),
                @Index(name = "idx_zipCode", columnList = "zipCode")
        })
@Data
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String street;

    @Column( length = 20)
    private String number;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 50)
    private String state;

    @Column(nullable = false, length = 50)
    private String country;

    @Column(name = "zip_code",nullable = false, length = 10)
    private String zipCode;

    public static class Builder {
        private String street;
        private String number;
        private String city;
        private String state;
        private String country;
        private String zipCode;

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public AddressModel build() {
            AddressModel addressModel = new AddressModel();
            addressModel.street = this.street;
            addressModel.number = this.number;
            addressModel.city = this.city;
            addressModel.state = this.state;
            addressModel.country = this.country;
            addressModel.zipCode = this.zipCode;

            return addressModel;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}

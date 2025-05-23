package br.com.sh.apiexample.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_address")
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String street;

    private String number;

    private String city;

    private String state;

    private String country;

    private String zipCode;

}

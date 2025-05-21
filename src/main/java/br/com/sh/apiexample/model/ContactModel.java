package br.com.sh.apiexample.model;

import jakarta.persistence.*;

@Entity()
@Table(name = "tb_contact")
public class ContactModel {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String phone;

    private String mobile;

    private String emailSecundary;



}

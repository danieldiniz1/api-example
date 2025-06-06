package br.com.sh.apiexample.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_contact")
@Data
public class ContactModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 50)
    private String phone;

    @Column(length = 50)
    private String mobile;

    @Column(name = "email_secundary",length = 100)
    private String emailSecundary;

    public static class Builder {
        private String phone;
        private String mobile;
        private String emailSecundary;

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder mobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public Builder emailSecundary(String emailSecundary) {
            this.emailSecundary = emailSecundary;
            return this;
        }

        public ContactModel build() {
            ContactModel contactModel = new ContactModel();
            contactModel.phone = this.phone;
            contactModel.mobile = this.mobile;
            contactModel.emailSecundary = this.emailSecundary;
            return contactModel;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmailSecundary() {
        return emailSecundary;
    }

    public void setEmailSecundary(String emailSecundary) {
        this.emailSecundary = emailSecundary;
    }
}

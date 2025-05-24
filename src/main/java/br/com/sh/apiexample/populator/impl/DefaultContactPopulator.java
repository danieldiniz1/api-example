package br.com.sh.apiexample.populator.impl;

import br.com.sh.apiexample.model.ContactModel;
import br.com.sh.apiexample.model.form.ContactForm;
import br.com.sh.apiexample.populator.Populator;
import org.springframework.stereotype.Component;

@Component
public class DefaultContactPopulator implements Populator<ContactForm, ContactModel> {
    @Deprecated(since = "1.0", forRemoval = true)
    @Override
    public ContactModel populate(ContactForm source, ContactModel target) {
        target.setMobile(source.mobile());
        target.setPhone(source.phone());
        target.setEmailSecundary(source.emailSecundary());
        return null;
    }

    @Override
    public ContactModel populate(ContactForm source) {
        return new ContactModel.Builder()
                .mobile(source.mobile())
                .phone(source.phone())
                .emailSecundary(source.emailSecundary())
                .build();
    }
}

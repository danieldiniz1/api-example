
CREATE TABLE IF NOT EXISTS tb_address (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    street VARCHAR(100) NOT NULL,
    number VARCHAR(20),
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    zip_code VARCHAR(10) NOT NULL,
    CONSTRAINT uq_addr UNIQUE (street, zip_code, city, state, country, number)
);

CREATE INDEX idx_general ON tb_address (street, city, state, zip_code, country, number);
CREATE INDEX idx_street ON tb_address (street);
CREATE INDEX idx_city ON tb_address (city);
CREATE INDEX idx_state ON tb_address (state);
CREATE INDEX idx_zipCode ON tb_address (zip_code);

CREATE TABLE IF NOT EXISTS tb_contact (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    phone VARCHAR(50),
    mobile VARCHAR(50),
    email_secundary VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS tb_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    cpf VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    address_id BIGINT,
    contact_id BIGINT,
    active BOOLEAN,
    CONSTRAINT fk_user_address FOREIGN KEY (address_id) REFERENCES tb_address(id),
    CONSTRAINT fk_user_contact FOREIGN KEY (contact_id) REFERENCES tb_contact(id)
);

CREATE INDEX idx_first_name ON tb_user (first_name);
CREATE INDEX idx_name ON tb_user (last_name, first_name);
CREATE INDEX idx_cpf ON tb_user (cpf);


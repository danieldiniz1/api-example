package br.com.sh.apiexample.repository;

import br.com.sh.apiexample.model.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel, Long> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
           "FROM AddressModel a WHERE a.street = :street AND a.number = :number AND a.city = :city " +
           "AND a.state = :state AND a.country = :country AND a.zipCode = :zipCode")
    boolean existsByKeys(String street, String number, String city, String state, String country, String zipCode);

    @Query("SELECT a FROM AddressModel a WHERE a.street = :street AND a.number = :number AND a.city = :city " +
           "AND a.state = :state AND a.country = :country AND a.zipCode = :zipCode")
    Optional<AddressModel> findByKeys(String street, String number, String city, String state, String country, String zipCode);
}

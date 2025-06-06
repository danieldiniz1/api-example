package br.com.sh.apiexample.repository;

import br.com.sh.apiexample.model.UserModel;
import br.com.sh.apiexample.model.dto.UserDto;
import br.com.sh.apiexample.model.projection.UserProjectionDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    @Query(value = "SELECT u.first_name, u.last_name, u.email FROM tb_user u WHERE u.active = true",
            nativeQuery = true)
    List<UserProjectionDto> findall();

    Optional<UserProjectionDto> findByCpfAndActive(String cpf, boolean active);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_user u SET u.email = :email WHERE u.cpf = :cpf",
            nativeQuery = true)
    void updateUser(String email, String cpf);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_user u SET u.active = false WHERE u.cpf = :cpf",
            nativeQuery = true)
    void logicDeleteByCpf(String cpf);
}

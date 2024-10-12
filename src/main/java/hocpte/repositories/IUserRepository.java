package hocpte.repositories;

import hocpte.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findUserEntityByUserNameOrEmail(String userName, String email);

    UserEntity findByUserName(String username);

    UserEntity findByEmail(String email);

    UserEntity findByPhone(String phone);
}

package hocpte.repositories;

import hocpte.entities.ClassRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IClassRegistrationRepository extends JpaRepository<ClassRegistrationEntity, Long> {
    @Query("select count(c) from ClassRegistrationEntity c where c.email = ?1 and c.status = ?2")
    Long countByEmail(String email, String status);

    @Query("select count(c) from ClassRegistrationEntity c where c.phone = ?1 and c.status = ?2")
    Long countByPhone(String phone, String status);
}

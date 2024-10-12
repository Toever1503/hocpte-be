package hocpte.repositories;

import hocpte.entities.OptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IOptionRepository extends JpaRepository<OptionEntity, Long> {

    Optional<OptionEntity> findByOptionKey(String optionKey) ;
}

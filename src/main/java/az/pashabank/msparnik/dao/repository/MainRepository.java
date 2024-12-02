package az.pashabank.msparnik.dao.repository;

import az.pashabank.msparnik.dao.entity.MainEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MainRepository extends CrudRepository<MainEntity, Long> {
    List<MainEntity> findByDevice(String device, Pageable pageable);
}

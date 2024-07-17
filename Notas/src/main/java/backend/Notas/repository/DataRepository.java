package backend.Notas.repository;

import backend.Notas.entities.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataRepository extends JpaRepository<Data, Long> {
    List<Data> findByArchived(boolean archived);
    List<Data> findByCategoriesContaining(String category);
}

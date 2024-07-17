package backend.Notas.rest.business;

import backend.Notas.entities.Data;
import backend.Notas.repository.DataRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DataBusiness {

    @Autowired
    private DataRepository repository;

    // Method to save data
    @Transactional
    public Data saveData(Data entity) {
        try {
            return repository.save(entity); // Save the data entity
        } catch (Exception e) {
            throw new RuntimeException("Error saving data: " + e.getMessage(), e); // Throw exception if save fails
        }
    }

    // Method to get all data
    @Transactional
    public List<Data> getAllData() {
        return (List<Data>) repository.findAll(); // Retrieve all data entities
    }

    // Method to get data by ID
    @Transactional
    public Optional<Data> getById(Long codeUser) {
        try {
            return repository.findById(codeUser); // Find data entity by ID
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener la entidad", e);
        }
    }

    // Method to delete data by ID
    @Transactional
    public Data deleteData(Long codeUser) {
        Optional<Data> optionalEntity = repository.findById(codeUser);
        if (optionalEntity.isPresent()) {
            repository.delete(optionalEntity.get()); // Delete data entity if present
            return optionalEntity.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entidad no encontrada"); // Throw exception if entity not found
        }
    }

    // Method to get data by category
    @Transactional
    public List<Data> getDataByCategory(String category) {
        return repository.findByCategoriesContaining(category); // Find data entities by category
    }

    // Method to update data
    @Transactional
    public Data updateData(Data entity, Long codeUser) {
        Optional<Data> optionalEntity = repository.findById(codeUser);
        if (optionalEntity.isPresent()) {
            return repository.save(entity); // Save updated data entity
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entidad no encontrada"); // Throw exception if entity not found
        }
    }

    // Method to get archived data
    @Transactional
    public List<Data> getArchivedData(boolean archived) {
        return repository.findByArchived(archived); // Retrieve archived data entities
    }
}

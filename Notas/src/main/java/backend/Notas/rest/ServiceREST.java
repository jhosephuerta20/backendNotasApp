package backend.Notas.rest;

import backend.Notas.entities.Data;
import backend.Notas.rest.business.DataBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ServiceREST {

    @Autowired
    private DataBusiness dataBusiness; // Autowired DataBusiness instance for handling business logic

    // Endpoint to save new data
    @PostMapping("/noteNew")
    public ResponseEntity<?> saveData(@RequestBody Data data) {
        try {
            Data savedData = dataBusiness.saveData(data); // Save data using business logic
            return ResponseEntity.ok(savedData); // Return saved data with OK status
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving data: " + e.getMessage()); // Handle exceptions with internal server error status
        }
    }

    // Endpoint to retrieve all data
    @GetMapping("/note")
    public List<Data> getInformation() {
        try {
            return dataBusiness.getAllData(); // Retrieve all data using business logic
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"); // Throw 404 status if data retrieval fails
        }
    }

    // Endpoint to retrieve data by ID
    @GetMapping("/note/{id}")
    public ResponseEntity<Data> getById(@PathVariable Long id) {
        try {
            Optional<Data> optionalData = dataBusiness.getById(id); // Retrieve data by ID using business logic
            if (optionalData.isPresent()) {
                return ResponseEntity.ok(optionalData.get()); // Return data if present with OK status
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"); // Throw 404 status if data not found
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting data", e); // Handle internal server error for other exceptions
        }
    }

    // Endpoint to update data
    @PutMapping("/updateNote")
    public Data updateData(@RequestBody Data data) {
        try {
            return dataBusiness.updateData(data, data.getId()); // Update data using business logic
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to update data"); // Throw 404 status if update fails
        }
    }

    // Endpoint to delete data by ID
    @DeleteMapping("/deleteNote/{id}")
    public Data deleteData(@PathVariable(value = "id") Long id) {
        return dataBusiness.deleteData(id); // Delete data using business logic
    }

    // Endpoint to update note by ID
    @PutMapping("/notes/{id}")
    public ResponseEntity<Data> updateNote(@PathVariable Long id, @RequestBody Data data) {
        if (data.getTag() == null || data.getTag().isEmpty() || data.getUsers() == null) {
            return ResponseEntity.badRequest().body(null); // Return bad request if tag or users are null or empty
        }
        Data existingData = dataBusiness.getById(id).orElse(null); // Get existing data by ID
        if (existingData == null) {
            return ResponseEntity.notFound().build(); // Return not found if data not present
        }
        // Update existing data with new values
        existingData.setTitle(data.getTitle());
        existingData.setContent(data.getContent());
        existingData.setTag(data.getTag());
        existingData.setUsers(data.getUsers());
        existingData.setArchived(data.isArchived());
        existingData.setCategories(data.getCategories());
        dataBusiness.saveData(existingData); // Save updated data using business logic
        return ResponseEntity.ok(existingData); // Return updated data with OK status
    }

    // Endpoint to retrieve archived notes
    @GetMapping("/notes/archived")
    public List<Data> getArchivedNotes(@RequestParam boolean archived) {
        return dataBusiness.getArchivedData(archived); // Retrieve archived notes using business logic
    }

    // Endpoint to retrieve notes by category
    @GetMapping("/notes/category/{category}")
    public List<Data> getNotesByCategory(@PathVariable String category) {
        return dataBusiness.getDataByCategory(category); // Retrieve notes by category using business logic
    }

}

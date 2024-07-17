package backend.Notas.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "data_labels")
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "users")
    private String users;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "tag")
    private String tag;

    @Column(name = "archived")
    private boolean archived;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "note_categories", joinColumns = @JoinColumn(name = "note_id"))
    @Column(name = "category")
    private List<String> categories;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}

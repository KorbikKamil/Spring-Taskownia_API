package pl.taskownia.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_project")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 10000)
    private String description;
    @Column(nullable = false)
    private ProjectStatus projectStatus;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToMany(mappedBy = "projectInterests") //TODO: other, maybe need JoinColumn
    private List<User> usersInterested;
    @ManyToOne
    @JoinColumn(name = "maker_id")
    private User maker;
    @Column(nullable = false)
    @Temporal(value= TemporalType.TIMESTAMP)
    private Date created_at;
    @Column(nullable = false)
    @Temporal(value= TemporalType.TIMESTAMP)
    private Date updated_at;

    public enum ProjectStatus {
        NEW, IN_PROGRESS, FINISHED
    }

    public Project() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public List<User> getUsersInterested() {
        return usersInterested;
    }

    public void setUsersInterested(List<User> usersInterested) {
        this.usersInterested = usersInterested;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getMaker() {
        return maker;
    }

    public void setMaker(User maker) {
        this.maker = maker;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}

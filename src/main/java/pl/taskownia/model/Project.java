package pl.taskownia.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.taskownia.serializer.ProjectSerializer;
import pl.taskownia.serializer.UserSerializer;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "projects")
@JsonSerialize(using = ProjectSerializer.class)
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
//    @ManyToMany(mappedBy = "projectInterests") //TODO: other, maybe need JoinColumn
//    private List<User> usersInterested;
    @ManyToOne
    @JoinColumn(name = "maker_id")
    private User maker;
    @Column(nullable = false)
    @Temporal(value= TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(nullable = false)
    @Temporal(value= TemporalType.TIMESTAMP)
    private Date updatedAt;

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

//    public List<User> getUsersInterested() {
//        return usersInterested;
//    }
//
//    public void setUsersInterested(List<User> usersInterested) {
//        this.usersInterested = usersInterested;
//    }

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
        return createdAt;
    }

    public void setCreated_at(Date created_at) {
        this.createdAt = created_at;
    }

    public Date getUpdated_at() {
        return updatedAt;
    }

    public void setUpdated_at(Date updated_at) {
        this.updatedAt = updated_at;
    }
}

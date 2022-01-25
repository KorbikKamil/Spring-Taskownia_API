package pl.taskownia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.taskownia.model.Project;
import pl.taskownia.model.ProjectChat;
import pl.taskownia.model.ProjectReview;
import pl.taskownia.service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveProject(HttpServletRequest r, @RequestBody Project project) {
        return projectService.newProject(r, project);
    }

    @PostMapping(path = "/take", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> takeProject(@RequestParam Long projId, HttpServletRequest r) {
        return projectService.takeProject(r, projId);
    }

    @PostMapping(path = "/{id}/finish", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> finishProject(@PathVariable Long id, @RequestBody ProjectReview review) {
        return projectService.finishProject(id, review);
    }

    @GetMapping(path = "/my-author", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getMyProjectsAuthor(HttpServletRequest r) {
        return projectService.getAllMyProjectsAuthor(r);
    }

    @GetMapping(path = "/my-maker", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getMyProjectsMaker(HttpServletRequest r) {
        return projectService.getAllMyProjectsMaker(r);
    }

    @PostMapping(path = "/{id}/send-message", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMessage(HttpServletRequest r, @PathVariable Long id, @RequestBody ProjectChat message) {
        return projectService.sendMessage(r, id, message);
    }

}

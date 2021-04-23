package pl.taskownia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.taskownia.model.Project;
import pl.taskownia.model.User;
import pl.taskownia.repository.ProjectRepository;
import pl.taskownia.service.ProjectService;
import pl.taskownia.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE) //TODO: get one page of projects
    public List<Project> getProjects(){
        return projectService.getAllProjects();
    }

    @PostMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveProject(HttpServletRequest r, @RequestBody Project project){
        return projectService.newProject(r, project);
    }

    @PostMapping(path = "/take", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveProject(@RequestParam Long projId, HttpServletRequest r){ return projectService.takeProject(r, projId); }

    @GetMapping(path = "/my-author", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getMyProjectsAuthor(HttpServletRequest r){
        return projectService.getAllMyProjectsAuthor(r);
    }

    @GetMapping(path = "/my-maker", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getMyProjectsMaker(HttpServletRequest r){ return projectService.getAllMyProjectsMaker(r); }

}

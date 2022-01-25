package pl.taskownia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.taskownia.data.ProjectChatResponse;
import pl.taskownia.model.Project;
import pl.taskownia.model.ProjectChat;
import pl.taskownia.model.ProjectReview;
import pl.taskownia.model.User;
import pl.taskownia.repository.ProjectChatRepository;
import pl.taskownia.repository.ProjectRepository;
import pl.taskownia.repository.ProjectReviewRepository;
import pl.taskownia.repository.UserRepository;
import pl.taskownia.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectReviewRepository projectReviewRepository;
    private final UserRepository userRepository;
    private final ProjectChatRepository projectChatRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<?> newProject(HttpServletRequest r, Project projectRequest) {
        User u = userRepository.findByUsername(jwtTokenProvider.getLogin(jwtTokenProvider.resolveToken(r)));
        projectRequest.setAuthor(u);
        projectRequest.setMaker(null);
        projectRequest.setProjectStatus(Project.ProjectStatus.NEW);
        projectRequest.setCreatedAt(new Date(System.currentTimeMillis()));
        projectRequest.setUpdatedAt(new Date(System.currentTimeMillis()));
        projectRepository.save(projectRequest);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> takeProject(HttpServletRequest r, Long projId) {
        User u = userRepository.findByUsername(jwtTokenProvider.getLogin(jwtTokenProvider.resolveToken(r)));
        Project p = projectRepository.findById(projId).orElse(null);
        if (p == null) {
            return new ResponseEntity<>("Project id is wrong!", HttpStatus.CONFLICT);
        }
        if (p.getMaker() != null) {
            return new ResponseEntity<>("Project already taken!", HttpStatus.CONFLICT);
        }
        if (p.getAuthor().getId() == u.getId()) {
            return new ResponseEntity<>("Author can't take project!", HttpStatus.CONFLICT);
        }
        p.setMaker(u);
        p.setProjectStatus(Project.ProjectStatus.IN_PROGRESS);
        projectRepository.save(p);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> finishProject(Long id, ProjectReview review) {
        Project p = projectRepository.findById(id).orElse(null);
        if (p == null) {
            return new ResponseEntity<>("Project id is wrong!", HttpStatus.CONFLICT);
        }

        projectReviewRepository.save(review);

        p.setProjectStatus(Project.ProjectStatus.FINISHED);
        p.setProjectReview(review);
        projectRepository.save(p);
        return ResponseEntity.ok().build();
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getAllMyProjectsAuthor(HttpServletRequest r) {
        User u = userRepository.findByUsername(jwtTokenProvider.getLogin(jwtTokenProvider.resolveToken(r)));

        return projectRepository.findAllByAuthor(u);
    }

    public List<Project> getAllMyProjectsMaker(HttpServletRequest r) {
        User u = userRepository.findByUsername(jwtTokenProvider.getLogin(jwtTokenProvider.resolveToken(r)));

        return projectRepository.findAllByMaker(u);
    }

    public Page<Project> getLastProjects(Integer howMany) {
        return projectRepository.findAll(
                PageRequest.of(0, howMany, Sort.by(Sort.Direction.DESC, "id")));
    }

    public ResponseEntity<?> sendMessage(HttpServletRequest r, Long id, ProjectChat message) {
        User u = userRepository.findByUsername(jwtTokenProvider.getLogin(jwtTokenProvider.resolveToken(r)));
        Project project = projectRepository.findById(id).orElse(null);
        if (project == null) {
            return new ResponseEntity<>("Project id is wrong!", HttpStatus.CONFLICT);
        }

        message.setProject(project);
        message.setUser(u);
        message.setDate(new Date(System.currentTimeMillis()));

        projectChatRepository.save(message);

        ProjectChatResponse response = new ProjectChatResponse();
        response.setProjectMessages(project.getMessages());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

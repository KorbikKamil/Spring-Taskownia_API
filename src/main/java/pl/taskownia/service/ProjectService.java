package pl.taskownia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.taskownia.model.Project;
import pl.taskownia.model.User;
import pl.taskownia.repository.ProjectRepository;
import pl.taskownia.repository.UserRepository;
import pl.taskownia.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<?> newProject(HttpServletRequest r, Project projectRequest) {
        User u = userRepository.findByUsername(jwtTokenProvider.getLogin(jwtTokenProvider.resolveToken(r)));

        projectRequest.setAuthor(u);
        projectRequest.setMaker(null);
        projectRequest.setCreated_at(new Date(System.currentTimeMillis()));
        projectRequest.setUpdated_at(new Date(System.currentTimeMillis()));
        projectRepository.save(projectRequest);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> takeProject(HttpServletRequest r, Long projId) {
        User u = userRepository.findByUsername(jwtTokenProvider.getLogin(jwtTokenProvider.resolveToken(r)));
        Project p = projectRepository.findById(projId).orElse(null);
        if(p == null) {
            return new ResponseEntity<>("Project id is wrong!", HttpStatus.CONFLICT);
        }
        if(p.getMaker()!=null) {
            return new ResponseEntity<>("Project already taken!", HttpStatus.CONFLICT);
        }
        if(p.getAuthor().getId()==u.getId()) {
            return new ResponseEntity<>("Author can't take project!", HttpStatus.CONFLICT);
        }
        p.setMaker(u);
        projectRepository.save(p);
        return ResponseEntity.ok().build();
    }

    public List<Project> getAllProjects() { //TODO: get max 10-20-30 projects, not all
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
        Page<Project> projectPage = projectRepository.findAll(
                PageRequest.of(0, howMany, Sort.by(Sort.Direction.DESC, "id")));
        return projectPage;
    }
}

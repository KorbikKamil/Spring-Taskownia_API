package pl.taskownia.data;

import lombok.Data;
import pl.taskownia.model.ProjectChat;

import java.util.List;

@Data
public class ProjectChatResponse {
    private List<ProjectChat> projectMessages;
}

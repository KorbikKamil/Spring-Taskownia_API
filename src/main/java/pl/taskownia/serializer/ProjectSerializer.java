package pl.taskownia.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import pl.taskownia.model.Project;

import java.io.IOException;

public class ProjectSerializer extends JsonSerializer<Project> {

    @Override
    public void serialize(Project project, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", project.getId());
        jsonGenerator.writeStringField("title", project.getTitle());
        jsonGenerator.writeStringField("description", project.getDescription());
        jsonGenerator.writeNumberField("authorId", project.getAuthor().getId());
        jsonGenerator.writeStringField("authorUsername", project.getAuthor().getUsername());
        jsonGenerator.writeObjectField("projectStatus", project.getProjectStatus());
        if (project.getMaker() != null) {
            jsonGenerator.writeNumberField("makerId", project.getMaker().getId());
            jsonGenerator.writeStringField("makerUsername", project.getMaker().getUsername());
        }
        jsonGenerator.writeObjectField("projectMessages", project.getMessages());
        jsonGenerator.writeObjectField("createdAt", project.getCreatedAt());
        jsonGenerator.writeObjectField("updatedAt", project.getUpdatedAt());
        jsonGenerator.writeObjectField("projectReview", project.getProjectReview());
        jsonGenerator.writeEndObject();
    }
}

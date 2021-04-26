package pl.taskownia.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.taskownia.model.Project;

import java.io.IOException;

public class ProjectSerializer extends StdSerializer<Project> {

    public ProjectSerializer(){this(null);}

    public ProjectSerializer(Class<Project> t) {
        super(t);
    }

    @Override
    public void serialize(Project project, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", project.getId());
        jsonGenerator.writeStringField("title", project.getTitle());
        jsonGenerator.writeStringField("description", project.getDescription());
        jsonGenerator.writeNumberField("authorId", project.getAuthor().getId());
        jsonGenerator.writeStringField("authorUsername", project.getAuthor().getUsername());
        jsonGenerator.writeObjectField("projectStatus", project.getProjectStatus());
        if(project.getMaker()!=null){
            jsonGenerator.writeNumberField("makerId", project.getMaker().getId());
            jsonGenerator.writeStringField("makerUsername", project.getMaker().getUsername());
        }
        jsonGenerator.writeObjectField("createdAt", project.getCreated_at());
        jsonGenerator.writeObjectField("updatedAt", project.getUpdated_at());
        jsonGenerator.writeEndObject();
    }
}

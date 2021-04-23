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
        jsonGenerator.writeNumberField("author_id", project.getAuthor().getId());
        jsonGenerator.writeStringField("author_username", project.getAuthor().getUsername());
        jsonGenerator.writeNumberField("maker_id", project.getMaker().getId());
        jsonGenerator.writeStringField("maker_username", project.getMaker().getUsername());
        jsonGenerator.writeObjectField("created_at", project.getCreated_at());
        jsonGenerator.writeObjectField("updated_at", project.getUpdated_at());
        jsonGenerator.writeEndObject();
    }
}

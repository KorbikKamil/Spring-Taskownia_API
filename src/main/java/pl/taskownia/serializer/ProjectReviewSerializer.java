package pl.taskownia.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.taskownia.model.ProjectReview;

import java.io.IOException;

public class ProjectReviewSerializer extends StdSerializer<ProjectReview> {
    public ProjectReviewSerializer(Class<ProjectReview> t) {
        super(t);
    }

    public ProjectReviewSerializer() {
        this(null);
    }

    @Override
    public void serialize(ProjectReview review, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("rating", review.getRating().getPoint());
        jsonGenerator.writeObjectField("review", review.getReview());
        jsonGenerator.writeEndObject();
    }
}

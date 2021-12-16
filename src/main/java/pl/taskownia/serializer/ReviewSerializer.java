package pl.taskownia.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.taskownia.model.Review;

import java.io.IOException;

public class ReviewSerializer extends StdSerializer<Review> {

    public ReviewSerializer() {
        this(null);
    }

    public ReviewSerializer(Class<Review> t) {
        super(t);
    }

    @Override
    public void serialize(Review review, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", review.getId());
        jsonGenerator.writeNumberField("authorId", review.getAuthor().getId());
        jsonGenerator.writeNumberField("reviewedId", review.getReviewed().getId());
        jsonGenerator.writeStringField("comment", review.getComment());
        jsonGenerator.writeNumberField("rating", review.getRating());
        jsonGenerator.writeObjectField("createdAt", review.getCreatedAt());
        jsonGenerator.writeObjectField("updatedAt", review.getUpdatedAt());
        jsonGenerator.writeEndObject();
    }
}

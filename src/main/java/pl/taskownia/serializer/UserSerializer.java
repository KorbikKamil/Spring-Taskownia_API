package pl.taskownia.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.taskownia.model.User;

import java.io.IOException;

public class UserSerializer extends StdSerializer<User> {
    public UserSerializer(Class<User> t) {
        super(t);
    }

    public UserSerializer() {
        this(null);
    }

    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", user.getId());
        jsonGenerator.writeStringField("username", user.getUsername());
        jsonGenerator.writeStringField("email", user.getEmail());
        jsonGenerator.writeObjectField("roles", user.getRoles());
        jsonGenerator.writeObjectField("status", user.getMakerStatus());
        jsonGenerator.writeStringField("first_name", user.getPersonalData().getFirstName());
        jsonGenerator.writeStringField("last_name", user.getPersonalData().getLastName());
        jsonGenerator.writeStringField("phone", user.getPersonalData().getPhone());
        jsonGenerator.writeObjectField("birth_date", user.getPersonalData().getBirthDate());
        jsonGenerator.writeStringField("city", user.getAddress().getCity());
        jsonGenerator.writeStringField("state", user.getAddress().getState());
        jsonGenerator.writeStringField("country", user.getAddress().getCountry());
        jsonGenerator.writeStringField("zip_code", user.getAddress().getZipCode());
        jsonGenerator.writeStringField("image", user.getImage().getImage_path());
        jsonGenerator.writeObjectField("created_at", user.getCreated_at());
        jsonGenerator.writeObjectField("updated_at", user.getUpdated_at());
        jsonGenerator.writeEndObject();
    }
}

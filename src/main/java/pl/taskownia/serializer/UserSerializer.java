package pl.taskownia.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.taskownia.model.Role;
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
        if(user.getRoles().get(0).equals(Role.ROLE_CLIENT_MAKER))
            jsonGenerator.writeObjectField("makerStatus", user.getMakerStatus());
        jsonGenerator.writeStringField("firstName", user.getPersonalData().getFirstName());
        jsonGenerator.writeStringField("lastName", user.getPersonalData().getLastName());
        jsonGenerator.writeStringField("phone", user.getPersonalData().getPhone());
        jsonGenerator.writeStringField("birthDate", user.getPersonalData().getBirthDate());
        jsonGenerator.writeStringField("city", user.getAddress().getCity());
        jsonGenerator.writeStringField("state", user.getAddress().getState());
        jsonGenerator.writeStringField("country", user.getAddress().getCountry());
        //jsonGenerator.writeStringField("zipCode", user.getAddress().getZipCode());
        jsonGenerator.writeStringField("image", user.getImage().getImage_path());
        if(user.getRoles().get(0).equals(Role.ROLE_CLIENT_AUTHOR))
            jsonGenerator.writeObjectField("projectsAuthor", user.getProjectsAuthor());
        if(user.getRoles().get(0).equals(Role.ROLE_CLIENT_MAKER))
            jsonGenerator.writeObjectField("projectsMaker", user.getProjectsMaker());
        jsonGenerator.writeObjectField("createdAt", user.getCreated_at());
        jsonGenerator.writeObjectField("updatedAt", user.getUpdated_at());
        jsonGenerator.writeEndObject();
    }
}

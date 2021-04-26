package pl.taskownia.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.taskownia.model.Chat;

import java.io.IOException;

public class ChatSerializer extends StdSerializer<Chat> {
    public ChatSerializer(Class<Chat> t) {
        super(t);
    }

    public ChatSerializer() {
        this(null);
    }

    @Override
    public void serialize(Chat chat, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
//        jsonGenerator.writeNumberField("id", chat.getId());
        jsonGenerator.writeStringField("message", chat.getMessage());
        jsonGenerator.writeObjectField("date", chat.getDate());
        jsonGenerator.writeNumberField("userId", chat.getUser().getId());
        jsonGenerator.writeStringField("userUsername", chat.getUser().getUsername());
//        jsonGenerator.writeObjectField("user_roles", chat.getUser().getRoles());
        jsonGenerator.writeEndObject();
    }
}

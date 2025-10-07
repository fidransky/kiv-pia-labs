package cz.zcu.kiv.pia.labs.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.util.Base64;

public class ByteArrayResourceSerializer extends JsonSerializer<ByteArrayResource> {
    @Override
    public void serialize(ByteArrayResource value, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        byte[] bytes = value.getByteArray();

        String base64String = Base64.getEncoder().encodeToString(bytes);

        generator.writeString(base64String);
    }
}

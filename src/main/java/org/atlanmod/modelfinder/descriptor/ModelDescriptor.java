package org.atlanmod.modelfinder.descriptor;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MdeForgeDescriptor.class, name = "MDEForge"),
        @JsonSubTypes.Type(value = UrlDescriptor.class, name = "url")
})
@Data
public abstract class ModelDescriptor {

    private String name;

}

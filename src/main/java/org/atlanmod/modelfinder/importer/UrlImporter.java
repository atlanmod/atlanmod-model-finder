package org.atlanmod.modelfinder.importer;

import lombok.NonNull;
import org.atlanmod.modelfinder.descriptor.UrlDescriptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class UrlImporter implements ModelImporter<UrlDescriptor> {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public InputStream importModel(@NonNull UrlDescriptor descriptor) {
        String content = restTemplate.getForObject(descriptor.getUrl(), String.class);
        if(content == null) {
            throw new NullPointerException("Content of " + descriptor.getUrl() + " is null");
        }
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    }
}

package org.atlanmod.modelfinder.importer;

import com.fasterxml.jackson.databind.JsonNode;
import org.atlanmod.modelfinder.descriptor.MdeForgeDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class MdeForgeImporter implements ModelImporter<MdeForgeDescriptor> {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Override
    public InputStream importModel(MdeForgeDescriptor descriptor) {
        String url = descriptor.getUrl() + "/store/artifact/metamodel/" + descriptor.getMetamodel();
        JsonNode node = restTemplateBuilder.build().getForObject(url, JsonNode.class);
        System.out.println(node.get("returnedData").get("content"));
        System.out.println(node);
        return null;
    }
}

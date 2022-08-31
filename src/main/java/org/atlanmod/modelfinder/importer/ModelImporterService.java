package org.atlanmod.modelfinder.importer;

import lombok.AllArgsConstructor;
import org.atlanmod.modelfinder.descriptor.Descriptor;
import org.atlanmod.modelfinder.descriptor.MdeForgeDescriptor;
import org.atlanmod.modelfinder.descriptor.UrlDescriptor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@AllArgsConstructor
public class ModelImporterService {

    private MdeForgeImporter mdeForgeImporter;

    private UrlImporter urlImporter;

    public void importDescriptor(Descriptor descriptor, File outputDirectory) {
        descriptor.getModels().forEach(modelDescriptor -> {
            InputStream is = null;
            if(modelDescriptor instanceof MdeForgeDescriptor) {
                is = mdeForgeImporter.importModel((MdeForgeDescriptor) modelDescriptor);
            } else if(modelDescriptor instanceof UrlDescriptor) {
                is = urlImporter.importModel((UrlDescriptor) modelDescriptor);
            }
            File outputFile = new File(outputDirectory, modelDescriptor.getName());
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                fileOutputStream.write(is.readAllBytes());
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}

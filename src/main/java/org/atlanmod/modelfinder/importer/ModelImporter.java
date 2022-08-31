package org.atlanmod.modelfinder.importer;

import org.atlanmod.modelfinder.descriptor.ModelDescriptor;

import java.io.InputStream;

public interface ModelImporter<T extends ModelDescriptor> {

    InputStream importModel(T descriptor);
}

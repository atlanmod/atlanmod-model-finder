package org.atlanmod.modelfinder.descriptor;

import lombok.Data;

import java.util.List;

@Data
public class Descriptor {

    private int version;

    private List<ModelDescriptor> models;
}

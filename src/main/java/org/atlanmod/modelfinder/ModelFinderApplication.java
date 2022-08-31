package org.atlanmod.modelfinder;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.atlanmod.modelfinder.descriptor.Descriptor;
import org.atlanmod.modelfinder.importer.ModelImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@SpringBootApplication
public class ModelFinderApplication implements ApplicationRunner {

	private static final String DEFAULT_OUTPUT_DIRECTORY_PATH = "workspace";

	private static final String DESCRIPTOR_OPTION = "descriptor";

	private static final String OUTPUT_OPTION = "output";

	@Autowired
	private ModelImporterService importer;

	public static void main(String[] args) {
		SpringApplication.run(ModelFinderApplication.class, args).close();
	}

	@Override
	public void run(ApplicationArguments args) {
		if(args.containsOption("test")) {
			log.info("Starting in test mode");
			return;
		}
		File outputDirectory = this.getOutputDirectory(args);
		Descriptor descriptor = this.getDescriptor(args);
		importer.importDescriptor(descriptor, outputDirectory);
	}

	private Descriptor getDescriptor(ApplicationArguments args) {
		if(!args.containsOption(DESCRIPTOR_OPTION)) {
			throw new RuntimeException("Option --file is mandatory");
		}
		List<String> descriptorOptions = args.getOptionValues(DESCRIPTOR_OPTION);
		if(descriptorOptions.size() != 1) {
			throw new IllegalArgumentException("Expected a single value for --descriptor, found " + descriptorOptions.size());
		}
		File descriptorFile = new File(descriptorOptions.get(0));
		if(!descriptorFile.exists()) {
			throw new RuntimeException("File " + descriptorFile.getAbsolutePath() + " does not exist");
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(descriptorFile, Descriptor.class);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	private File getOutputDirectory(ApplicationArguments args) {
		File outputDirectoryFile = new File(DEFAULT_OUTPUT_DIRECTORY_PATH);
		if(args.containsOption(OUTPUT_OPTION)) {
			List<String> outputDirectoryOptions = args.getOptionValues(OUTPUT_OPTION);
			if(outputDirectoryOptions.size() != 1) {
				throw new IllegalArgumentException("Expected a single value for --output, found " + outputDirectoryOptions.size());
			}
			outputDirectoryFile = new File(outputDirectoryOptions.get(0));
		}
		if(!outputDirectoryFile.exists()) {
			outputDirectoryFile.mkdirs();
		}
		return outputDirectoryFile;
	}

}

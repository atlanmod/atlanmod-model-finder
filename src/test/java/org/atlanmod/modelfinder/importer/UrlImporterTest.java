package org.atlanmod.modelfinder.importer;

import org.atlanmod.modelfinder.descriptor.UrlDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(args = "--test")
public class UrlImporterTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UrlImporter urlImporter;

    @BeforeEach
    public void setUp() {
        when(restTemplate.getForObject(anyString(), any(Class.class)))
                .thenReturn("test");
    }

    @Test
    public void importModelNullDescriptor() {
        assertThrows(NullPointerException.class, () ->urlImporter.importModel(null));
    }

    @Test
    public void importModelValidDescriptor() throws IOException {
        UrlDescriptor descriptor = new UrlDescriptor();
        descriptor.setUrl("http://a-valid.url");
        InputStream is = urlImporter.importModel(descriptor);
        String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        assertThat(content).isEqualTo("test");
        is.close();
    }
}

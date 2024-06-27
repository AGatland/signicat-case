package signicat_case.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import signicat_case.model.UsageStatistic;
import signicat_case.repository.UsageStatisticRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FileControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UsageStatisticRepository usageStatisticRepository;

    @BeforeEach
    public void setup() {
        // Clear the repository before each test
        usageStatisticRepository.deleteAll();
    }

    // https://www.baeldung.com/spring-multipart-post-request-test
    @Test
    public void testFileZip_success() throws Exception {
        // Set up Mock data
        MockMultipartFile file1 = new MockMultipartFile("files", "file1.txt", "text/plain", "content".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("files", "file2.txt", "text/plain", "content".getBytes());
        UsageStatistic usageStatistic = new UsageStatistic("127.0.0.1");

        // Mock request endpoint, expect zip file
        mockMvc.perform(multipart("/file/zip")
                .file(file1)
                .file(file2))
                .andExpect(status().isOk()).andExpect(content().bytes(createMockZip()));

        // Fetch the entry from the database
        Optional<UsageStatistic> optionalUsageStatistic = usageStatisticRepository.findById(1);

        // Check that the entry exists
        assertTrue(optionalUsageStatistic.isPresent(), "UsageStatistic should be present in the database");

        // Verify that the retrieved entry matches the expected value
        UsageStatistic expectedUsageStatistic = optionalUsageStatistic.get();
        assertEquals(expectedUsageStatistic.getIpAddress(), usageStatistic.getIpAddress());
        assertEquals(expectedUsageStatistic.getUsageCount(), usageStatistic.getUsageCount());
        assertEquals(expectedUsageStatistic.getDate(), usageStatistic.getDate());
    }

    @Test
    public void testFileZip_emptyFile() throws Exception {
        byte[] emptyContent = new byte[0]; // 0 byte (empty) file
        MockMultipartFile largeFile = new MockMultipartFile("files", "emptyFile.txt", "text/plain", emptyContent);

        MvcResult result = mockMvc.perform(multipart("/file/zip")
                .file(largeFile))
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertEquals("File \"emptyFile.txt\" is not valid.", response);
    }
    
    public static byte[] createMockZip() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            // Add entries to the ZIP file
            ZipEntry entry1 = new ZipEntry("file1.txt");
            zos.putNextEntry(entry1);
            zos.write("content".getBytes());
            zos.closeEntry();

            ZipEntry entry2 = new ZipEntry("file2.txt");
            zos.putNextEntry(entry2);
            zos.write("content".getBytes());
            zos.closeEntry();
        }
        return baos.toByteArray();
    }
}

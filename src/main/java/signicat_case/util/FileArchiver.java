package signicat_case.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileArchiver {

    // Source: https://www.baeldung.com/java-compress-and-uncompress
    public byte[] archiveFiles(MultipartFile[] files) throws IOException {
        // Hold zipped data in memory
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // try-with-resource ensures properly closing
        try (ZipOutputStream zipOut = new ZipOutputStream(baos)) {
            for (MultipartFile file : files) {
                ZipEntry zipEntry = new ZipEntry(file.getOriginalFilename());
                zipOut.putNextEntry(zipEntry);
                zipOut.write(file.getBytes());
                zipOut.closeEntry();
            }
        }
        // return zip bytes
        return baos.toByteArray();
    }
}

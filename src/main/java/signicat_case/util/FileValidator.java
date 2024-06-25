package signicat_case.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileValidator {

    public boolean isFileValid(MultipartFile file) {
        // Can also validate contentType in the future if needed
        if (file.isEmpty() || file == null) {
            return false;
        }
        return true;
    }

    public boolean isFilesValid(MultipartFile[] files) {
        for (MultipartFile file: files) {
            if (!isFileValid(file)) {
                return false;
            }
        }
        return true;
    }
    
}

package signicat_case.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import signicat_case.exception.FileValidationException;

@Service
public class FileValidator {

    public boolean isFileValid(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileValidationException("File \"" + (file != null ? file.getOriginalFilename() : "null") + "\" is not valid.");
        }
        return true;
    }

    public boolean isFilesValid(MultipartFile[] files) {
        for (MultipartFile file : files) {
            isFileValid(file);
        }
        return true;
    }
    
}

package signicat_case.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import signicat_case.service.FileService;

@RestController
@RequestMapping("file")
public class FileController {
    
    @Autowired
    FileService fileService;

    @GetMapping("/test")
    public ResponseEntity<?> testEndpoint() {
        return ResponseEntity.ok(fileService.testConnection());
    }

    // Source: https://spring.io/guides/gs/uploading-files && https://stackoverflow.com/questions/27952949/spring-rest-create-zip-file-and-send-it-to-the-client
    @PostMapping(value="/zip", produces="application/zip")
    public ResponseEntity<?> zipFiles(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
        try {
            byte[] zipFileBytes = fileService.processFilesForZipping(files, request.getRemoteAddr());
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"archive.zip\"")
                .body(zipFileBytes);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error zipping files");
        }
    }
}

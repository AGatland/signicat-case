package signicat_case.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

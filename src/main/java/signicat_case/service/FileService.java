package signicat_case.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import signicat_case.util.FileArchiver;

import signicat_case.repository.UsageStatisticRepository;

@Service
public class FileService {

    @Autowired
    UsageStatisticRepository usageStatisticRepository;

    @Autowired
    FileArchiver fileArchiver;

    public String testConnection() {
        return "Working";
    }

    public byte[] processFilesForZipping(MultipartFile[] files, String ipAddress) throws IOException {
        // TODO: Validate files either here or while archiving

        // Archive files
        byte[] zipFileBytes = fileArchiver.archiveFiles(files);

        return zipFileBytes;
    }
    
}

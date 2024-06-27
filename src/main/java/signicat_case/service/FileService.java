package signicat_case.service;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import signicat_case.util.FileArchiver;
import signicat_case.util.FileValidator;
import signicat_case.model.UsageStatistic;
import signicat_case.repository.UsageStatisticRepository;

@Service
public class FileService {

    @Autowired
    UsageStatisticRepository usageStatisticRepository;

    @Autowired
    FileArchiver fileArchiver;

    @Autowired
    FileValidator fileValidator;

    public byte[] processFilesForZipping(MultipartFile[] files, String ipAddress) throws IOException {
        // Validate files before processing
        fileValidator.isFilesValid(files);

        // Archive files
        byte[] zipFileBytes = fileArchiver.archiveFiles(files);

        // Get ip & date entry if exists
        UsageStatistic usageStatistic = usageStatisticRepository.findByIpAddressAndDate(ipAddress, LocalDate.now());

        // Add entry in DB if it doesnt exist, increment usageCount if it does
        if (usageStatistic == null) {
            UsageStatistic newUsageStatistic = new UsageStatistic(ipAddress);
            usageStatisticRepository.save(newUsageStatistic);
        } else {
            usageStatistic.setUsageCount(usageStatistic.getUsageCount()+1);
            usageStatisticRepository.save(usageStatistic);
        }

        return zipFileBytes;
    }
    
}

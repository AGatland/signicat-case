package signicat_case.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import signicat_case.repository.UsageStatisticRepository;

@Service
public class FileService {

    @Autowired
    UsageStatisticRepository usageStatisticRepository;

    public String testConnection() {
        return "Working";
    }
    
}

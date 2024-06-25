package signicat_case.repository;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import signicat_case.model.UsageStatistic;

public interface UsageStatisticRepository extends JpaRepository<UsageStatistic, Integer> {
    
    UsageStatistic findByIpAddressAndDate(String ipAddress, LocalDate date);
}

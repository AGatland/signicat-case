package signicat_case.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "UsageStatistics")
public class UsageStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "usage_count", nullable = false)
    private int usageCount;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public UsageStatistic(String ipAddress) {
        this.ipAddress = ipAddress;
        this.usageCount = 1;
        this.date = LocalDate.now();
    }
}

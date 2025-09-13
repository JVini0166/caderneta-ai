package com.cadernetaai.cadernetaai.repository;

import com.cadernetaai.cadernetaai.domain.DailyLog;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyLogRepository extends JpaRepository<DailyLog, Long> {
    Optional<DailyLog> findByProjectIdAndLogDate(Long projectId, LocalDate logDate);
}



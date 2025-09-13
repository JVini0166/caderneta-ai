package com.cadernetaai.cadernetaai.service;

import com.cadernetaai.cadernetaai.domain.DailyLog;
import com.cadernetaai.cadernetaai.domain.Project;
import com.cadernetaai.cadernetaai.repository.DailyLogRepository;
import com.cadernetaai.cadernetaai.repository.ProjectRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DailyLogService {
    private final DailyLogRepository dailyLogRepository;
    private final ProjectRepository projectRepository;
    private final RbacService rbacService;

    public DailyLogService(DailyLogRepository dailyLogRepository, ProjectRepository projectRepository, RbacService rbacService) {
        this.dailyLogRepository = dailyLogRepository;
        this.projectRepository = projectRepository;
        this.rbacService = rbacService;
    }

    @Transactional
    public DailyLog create(Long workspaceId, Long userId, Long projectId, LocalDate date, String observations, Double lat, Double lon) {
        if (!rbacService.canRegisterDailyLog(workspaceId, userId)) throw new SecurityException("Not allowed");
        Project project = projectRepository.findById(projectId).orElseThrow();
        dailyLogRepository.findByProjectIdAndLogDate(projectId, date).ifPresent(d -> { throw new IllegalStateException("Daily log already exists"); });
        DailyLog log = new DailyLog();
        log.setProject(project);
        log.setLogDate(date);
        log.setCreatedBy(userId);
        log.setObservations(observations);
        log.setLatitude(lat == null ? null : BigDecimal.valueOf(lat));
        log.setLongitude(lon == null ? null : BigDecimal.valueOf(lon));
        return dailyLogRepository.save(log);
    }
}



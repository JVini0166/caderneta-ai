package com.cadernetaai.cadernetaai.controller;

import com.cadernetaai.cadernetaai.domain.DailyLog;
import com.cadernetaai.cadernetaai.service.DailyLogService;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/daily-logs")
public class DailyLogController {
    public static class CreateDailyLogRequest {
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        public LocalDate date;
        public String observations;
        public Double lat;
        public Double lon;
    }

    private final DailyLogService dailyLogService;

    public DailyLogController(DailyLogService dailyLogService) { this.dailyLogService = dailyLogService; }

    @PostMapping
    public ResponseEntity<DailyLog> create(@RequestParam Long workspaceId,
                                           @RequestParam Long projectId,
                                           @RequestBody CreateDailyLogRequest body) {
        Long userId = com.cadernetaai.cadernetaai.controller.security.AuthUtil.currentUserId();
        DailyLog log = dailyLogService.create(workspaceId, userId, projectId, body.date, body.observations, body.lat, body.lon);
        return ResponseEntity.ok(log);
    }
}


package com.example.attendance.service;

import com.example.attendance.entity.Attendance;
import com.example.attendance.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AttendanceService {

    private final AttendanceRepository repository;

    public AttendanceService(AttendanceRepository repository) {
        this.repository = repository;
    }

    // 今日の勤怠取得 or 作成
    public Attendance getToday() {
        return repository.findAll().stream()
                .filter(a -> a.getWorkDate().equals(LocalDate.now()))
                .findFirst()
                .orElseGet(() -> {
                    Attendance a = new Attendance();
                    a.setWorkDate(LocalDate.now());
                    return repository.save(a);
                });
    }

    // 出勤
    public void clockIn() {
        Attendance a = getToday();

        if (a.getClockIn() != null) {
            throw new RuntimeException("すでに出勤済み");
        }

        a.setClockIn(LocalDateTime.now());
        repository.save(a);
    }

    // 中抜け開始
    public void breakStart() {
        Attendance a = getToday();

        if (a.getClockIn() == null) {
            throw new RuntimeException("出勤していません");
        }

        if (a.getBreakStart() != null) {
            throw new RuntimeException("すでに中抜け中");
        }

        a.setBreakStart(LocalDateTime.now());
        repository.save(a);
    }

    // 中抜け終了
    public void breakEnd() {
        Attendance a = getToday();

        if (a.getBreakStart() == null) {
            throw new RuntimeException("中抜けしていません");
        }

        if (a.getBreakEnd() != null) {
            throw new RuntimeException("すでに中抜け終了済み");
        }

        a.setBreakEnd(LocalDateTime.now());
        repository.save(a);
    }

    // 退勤
    public void clockOut() {
        Attendance a = getToday();

        if (a.getClockIn() == null) {
            throw new RuntimeException("出勤していません");
        }

        if (a.getClockOut() != null) {
            throw new RuntimeException("すでに退勤済み");
        }

        a.setClockOut(LocalDateTime.now());
        repository.save(a);
    }

    public Attendance getTodayAttendance() {
        return repository.findAll().stream()
            .filter(a -> a.getWorkDate().equals(LocalDate.now()))
            .findFirst()
            .orElse(null);
    }
 
    public String getStatus(Attendance a) {
        if (a == null || a.getClockIn() == null) {
            return "未出勤";
        }
        if (a.getClockOut() != null) {
            return "退勤済み";
        }
        if (a.getBreakStart() != null && a.getBreakEnd() == null) {
            return "中抜け中";
        }
        return "勤務中";
    }

    public long calculateWorkMinutes(Attendance a) {

        if (a == null || a.getClockIn() == null) {
            return 0;
        }

        LocalDateTime end = (a.getClockOut() != null)
                ? a.getClockOut()
                : LocalDateTime.now();

        long totalMinutes = java.time.Duration.between(a.getClockIn(), end).toMinutes();

        // 休憩引く
        if (a.getBreakStart() != null) {
            LocalDateTime breakEnd = (a.getBreakEnd() != null)
                    ? a.getBreakEnd()
                    : LocalDateTime.now();

            long breakMinutes = java.time.Duration.between(a.getBreakStart(), breakEnd).toMinutes();
            totalMinutes -= breakMinutes;
        }

        return Math.max(totalMinutes, 0);
    }

    public String formatMinutes(long minutes) {
        long hours = minutes / 60;
        long mins = minutes % 60;
        return hours + "時間 " + mins + "分";
    }
}
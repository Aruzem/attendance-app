package com.example.attendance.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate workDate;
    private LocalDateTime clockIn;
    private LocalDateTime clockOut;
    private LocalDateTime breakStart;
    private LocalDateTime breakEnd;

    // getter/setter
    public Long getId() { return id; }

    public LocalDate getWorkDate() { return workDate; }
    public void setWorkDate(LocalDate workDate) { this.workDate = workDate; }

    public LocalDateTime getClockIn() { return clockIn; }
    public void setClockIn(LocalDateTime clockIn) { this.clockIn = clockIn; }

    public LocalDateTime getClockOut() { return clockOut; }
    public void setClockOut(LocalDateTime clockOut) { this.clockOut = clockOut; }

    public LocalDateTime getBreakStart() { return breakStart; }
    public void setBreakStart(LocalDateTime breakStart) { this.breakStart = breakStart; }

    public LocalDateTime getBreakEnd() { return breakEnd; }
    public void setBreakEnd(LocalDateTime breakEnd) { this.breakEnd = breakEnd; }
}
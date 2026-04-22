package com.example.attendance.controller;

import com.example.attendance.service.AttendanceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AttendanceController {

    private final AttendanceService service;

    public AttendanceController(AttendanceService service) {
        this.service = service;
    }

    @PostMapping("/clock-in")
    public String clockIn() {
        service.clockIn();
        return "redirect:/";
    }

    @PostMapping("/break-start")
    public String breakStart() {
        service.breakStart();
        return "redirect:/";
    }

    @PostMapping("/break-end")
    public String breakEnd() {
        service.breakEnd();
        return "redirect:/";
    }

    @PostMapping("/clock-out")
    public String clockOut() {
        service.clockOut();
        return "redirect:/";
    }
}
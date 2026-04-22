package com.example.attendance.controller;

import com.example.attendance.entity.Attendance;
import com.example.attendance.service.AttendanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final AttendanceService service;

    public ViewController(AttendanceService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home(Model model) {

        Attendance a = service.getTodayAttendance();
        String status = service.getStatus(a);

        long minutes = service.calculateWorkMinutes(a);

        model.addAttribute("attendance", a);
        model.addAttribute("status", status);
        model.addAttribute("minutes", minutes);

        return "home";
    }
}
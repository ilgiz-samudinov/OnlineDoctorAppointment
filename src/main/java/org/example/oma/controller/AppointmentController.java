package org.example.oma.controller;

import jakarta.servlet.http.HttpSession;
import org.example.oma.model.Appointment;
import org.example.oma.model.Client;
import org.example.oma.model.Doctor;
import org.example.oma.service.AppointmentService;
import org.example.oma.service.ClientService;
import org.example.oma.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final ClientService clientService;

    @Autowired
    public AppointmentController
            (AppointmentService appointmentService,
             DoctorService doctorService,
             ClientService clientService){

        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.clientService = clientService;
    }


    @GetMapping("/")
    public String home(){
        return "index";
    }


    @GetMapping("/check")
    public String check(){
        return "client-search";
    }

    @GetMapping("/search-client")
    public String search(@RequestParam("phone") String phone, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Optional<Client> client = clientService.findByPhone(phone);
        if (client.isPresent()) {
            // return "redirect:/appointment/select-client";
            model.addAttribute("client",client.get());

        } else {
            redirectAttributes.addFlashAttribute("message", "Клиент не найден. Пожалуйста, зарегистрируйтесь.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/client/create";
        }
        //model.addAttribute("phone", phone);
        session.setAttribute("clientId", client.get().getId());
        return "client-search";
    }


    @GetMapping("/select-client")
    public String selectClient(Model model){
        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);
        model.addAttribute("keyword", "");
        return "select-doctor";
    }

    @GetMapping("/search-doctor")
    public String searchDoctors(@RequestParam("keyword") String keyword, Model model) {
//        List<Doctor> doctors = doctorService.searchDoctors(keyword);

        List<Doctor> doctors;
        if (keyword == null || keyword.isEmpty() ) {
            doctors = doctorService.getAllDoctors();
        }
        else{
            doctors = doctorService.searchDoctors(keyword);
        }

        model.addAttribute("doctors", doctors);
        model.addAttribute("keyword", keyword);
        return "select-doctor";  // Имя шаблона для отображения результатов
    }


    @PostMapping("/select-doctor")
    public String selectDoctor(@RequestParam("doctorId")Long doctorId, HttpSession session , Model model) {
        session.setAttribute("doctorId" , doctorId);
        List<String> timeSlots = appointmentService.generateTimeSlots();
        model.addAttribute("timeSlots", timeSlots);
        return "select-date";
    }

    @PostMapping("/select-date")
    public String selectDate(@RequestParam("appointmentDate") String appointmentDate,
                             @RequestParam("appointmentTime") String appointmentTime,
                             HttpSession session) {

        LocalDate parsedAppointmentDate = LocalDate.parse(appointmentDate);
        LocalTime parsedAppointmentTime = LocalTime.parse(appointmentTime);

        session.setAttribute("appointmentDate", parsedAppointmentDate);
        session.setAttribute("appointmentTime", parsedAppointmentTime);

        return "forward:/appointment/save";
    }




    @PostMapping("/save")
    public String saveAppointment(HttpSession session, Model model) {
        Long clientId = (Long) session.getAttribute("clientId");
        Long doctorId = (Long) session.getAttribute("doctorId");
        LocalDate appointmentDate = (LocalDate) session.getAttribute("appointmentDate");
        LocalTime appointmentTime = (LocalTime) session.getAttribute("appointmentTime");

        // Проверка наличия данных в сессии
        if (clientId == null || doctorId == null || appointmentDate == null || appointmentTime == null) {
            return "redirect:/appointment/error";
        }

        Optional<Client> client = clientService.getClientById(clientId);
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);

        if (client.isPresent() && doctor.isPresent()) {
            appointmentService.saveAppointment(client.get(), doctor.get(), appointmentDate, appointmentTime);

            model.addAttribute("clientName", client.get().getLastName()  + " " + client.get().getFirstName() + " " + client.get().getMiddleName());
            model.addAttribute("appointmentDate", appointmentDate);
            model.addAttribute("appointmentTime", appointmentTime);
            model.addAttribute("doctorName", doctor.get().getLastName() + " " + doctor.get().getFirstName() + " " + doctor.get().getMiddleName());
            model.addAttribute("doctorSpecialty", doctor.get().getSpecialty());

            return "success";
        } else {
            return "redirect:/appointment/error";
        }
    }

    @GetMapping("/all")
    public String getAllAppointments(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        return "appointment-list";
    }




}

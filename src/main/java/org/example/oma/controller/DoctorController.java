package org.example.oma.controller;

import org.example.oma.model.Doctor;
import org.example.oma.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    public final DoctorService doctorService;
    @Autowired
    public DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }
    @GetMapping("/all")
    public String getAllDoctors(Model model){
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctor-list";
    }

    @GetMapping("/create")
    public String showCreateDoctorForm(Model model){
        model.addAttribute("doctor" , new Doctor());
        return "doctor-create";
    }
    @PostMapping("/create")
    public String createDoctor(@ModelAttribute("doctor") Doctor doctor){
        doctorService.saveDoctor(doctor);
        return "redirect:/doctor/all";
    }

    @GetMapping("/edit/{id}")
    public String showEditDoctorForm(@PathVariable("id") Long id, Model model){
        Optional<Doctor> doctor = doctorService.getDoctorById(id);
        if(doctor.isPresent()){
            model.addAttribute("doctor",doctor.get());
        }
        else{
            model.addAttribute("errorMessage", "Доктор не найден");
            return "error-page";
        }
        return "doctor-edit";

    }

    @PostMapping("/edit/{id}")
    public String updateDoctor(@PathVariable("id") Long id, @ModelAttribute("doctor")Doctor doctor){
        doctorService.updateDoctor(id,doctor);
        return "redirect:/doctor/all";
    }

    @PostMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable("id")Long id){
        doctorService.deleteDoctor(id);
        return "redirect:/doctor/all";
    }


    @GetMapping("/search")
    public String searchDoctors(@RequestParam("keyword") String keyword, Model model) {
        List<Doctor> doctors = doctorService.searchDoctors(keyword);
        model.addAttribute("doctors", doctors);
        model.addAttribute("keyword", keyword);
        return "select-doctor"; // Возвращает обновлённый шаблон
    }




}

package org.example.oma.service;

import org.example.oma.model.*;
import org.example.oma.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.example.oma.model.AppointmentStatus.WAITING;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private QueueService queueService;


    public Appointment saveAppointment(Client client, Doctor doctor, LocalDate appointmentDate, LocalTime appointmentTime) {
        Appointment appointment = new Appointment();
        appointment.setClient(client);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setAppointmentTime(appointmentTime);

        if (Arrays.asList("WAITING", "IN_PROGRESS", "COMPLETED", "CANCELLED").contains(AppointmentStatus.WAITING.toString())) {
            appointment.setStatus(AppointmentStatus.WAITING);
        } else {
            throw new IllegalArgumentException("Недопустимый статус: WAITING");
        }

        Appointment savedAppointment =  appointmentRepository.save(appointment);

        Queue queue = new Queue();
        queue.setClient(client);
        queue.setDoctor(doctor);
        queue.setAppointment(savedAppointment);
        queue.setCreatedAt(LocalDateTime.now());
        queue.setStatus(QueueStatus.WAITING);
        queueService.save(queue);
        return savedAppointment;
    }


    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }


    public List<String> generateTimeSlots(){
        List<String> timeSlots = new ArrayList<>();
        LocalTime startTime = LocalTime.of(8,0);
        LocalTime endTime = LocalTime.of(17, 0);

        while(!startTime.isAfter(endTime)){
            timeSlots.add(startTime.toString());
            startTime = startTime.plusMinutes(30);

        }
        return timeSlots;
    }






}



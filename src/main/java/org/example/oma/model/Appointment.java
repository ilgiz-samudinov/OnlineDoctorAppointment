package org.example.oma.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column (name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AppointmentStatus status;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    public Appointment(){}

    public Appointment(Long id, Client client, Doctor doctor, LocalDate appointmentDate, LocalTime appointmentTime, AppointmentStatus status) {
        this.id = id;
        this.client = client;
        this.doctor = doctor;
        this.appointmentDate=appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", appointmentTime=" + appointmentTime +
                ", status=" + status +
                '}';
    }

//    @Override
//    public String toString() {
//        return "Appointment{" +
//                "id=" + id +
//                ", client=" + client +
//                ", doctor=" + doctor +
//                ", appointmentTime=" + appointmentTime +
//                ", status=" + status +
//                '}';
//    }

}

package org.example.oma.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="queues")
public class Queue {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="appointment_id", nullable = false)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name="doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name="client_id", nullable = false)
    private Client client;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Enumerated (EnumType.STRING)
    @Column(name = "status", nullable = false)
    private QueueStatus status;

    public Queue(){}

    public Queue(Long id, Appointment appointment, Doctor doctor, Client client, LocalDateTime createdAt, QueueStatus status) {
        this.id = id;
        this.appointment = appointment;
        this.doctor = doctor;
        this.client = client;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public QueueStatus getStatus() {
        return status;
    }

    public void setStatus(QueueStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Queue{" +
                "id=" + id +
                ", appointment=" + appointment +
                ", doctor=" + doctor +
                ", client=" + client +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", status=" + status +
                '}';
    }


}

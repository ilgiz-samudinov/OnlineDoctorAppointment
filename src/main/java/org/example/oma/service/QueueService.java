package org.example.oma.service;

import org.example.oma.model.Doctor;
import org.example.oma.model.Queue;
import org.example.oma.model.QueueStatus;
import org.example.oma.repository.QueueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QueueService {
    private final QueueRepository queueRepository;

    public QueueService(QueueRepository queueRepository){
        this.queueRepository = queueRepository;
    }

    public Queue save(Queue queue){
        if (queueRepository.existsByClientIdAndDoctorId(queue.getClient().getId(), queue.getDoctor().getId())) {
            throw new IllegalStateException("Client is already in the queue for this doctor.");
        }
        queue.setCreatedAt(LocalDateTime.now()); // Установка времени добавления
        queue.setStatus(QueueStatus.WAITING);
        return queueRepository.save(queue);
    }


    public Queue updateStatus(Long queueId, QueueStatus newStatus) {
        Queue queue = queueRepository.findById(queueId)
                .orElseThrow(() -> new IllegalArgumentException("Queue not found with ID: " + queueId));
        queue.setStatus(newStatus);
        queue.setUpdatedAt(LocalDateTime.now());
        return queueRepository.save(queue);
    }

    public void delete(Long id) {
        if (!queueRepository.existsById(id)) {
            throw new IllegalArgumentException("Queue not found with ID: " + id);
        }
        queueRepository.deleteById(id);
    }

    public List<Queue> findAllQueue() {
        return queueRepository.findAllByOrderByCreatedAtAsc(); // Метод должен быть определен в `QueueRepository`
    }



    public List<Queue> findQueueByDoctor(Long doctorId) {
        return queueRepository.findByDoctorIdAndStatusOrderByCreatedAtAsc(doctorId, QueueStatus.WAITING);
    }


    public boolean isClientInQueue(Long clientId, Long doctorId) {
        return queueRepository.existsByClientIdAndDoctorId(clientId, doctorId);
    }

}

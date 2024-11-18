package org.example.oma.repository;

import org.example.oma.model.Queue;
import org.example.oma.model.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueRepository  extends JpaRepository<Queue, Long> {
    boolean existsByClientIdAndDoctorId(Long id, Long id1);

    List<Queue> findByDoctorIdAndStatusOrderByCreatedAtAsc(Long doctorId, QueueStatus queueStatus);

    List<Queue> findAllByOrderByCreatedAtAsc();
}

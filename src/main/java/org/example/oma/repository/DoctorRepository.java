package org.example.oma.repository;

import org.example.oma.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByFirstNameContainingOrLastNameContainingOrMiddleNameContainingOrSpecialtyContaining(
            String firstNameKeyword, String lastNameKeyword, String middleNameKeyword, String specialtyKeyword);
}

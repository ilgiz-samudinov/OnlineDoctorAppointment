package org.example.oma.service;

import org.example.oma.model.Doctor;
import org.example.oma.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    @Autowired
    public DoctorService(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    public Doctor saveDoctor(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Doctor updateDoctor(Long id, Doctor doctor) {
        Optional<Doctor> existingDoctor = getDoctorById(id);
        if (existingDoctor.isPresent()) {
            Doctor updateDoctor = existingDoctor.get();
            updateDoctor.setLastName(doctor.getLastName());
            updateDoctor.setFirstName(doctor.getFirstName());
            updateDoctor.setMiddleName(doctor.getMiddleName());
            updateDoctor.setSpecialty(doctor.getSpecialty());
            return doctorRepository.save(updateDoctor);
        } else {
            return null;
        }
    }

    public Optional<Doctor> getDoctorById(Long id){
        return doctorRepository.findById(id);
    }

    public void deleteDoctor(Long id){
        doctorRepository.deleteById(id);
    }



    public List<Doctor> searchDoctors(String keyword) {
        return doctorRepository.findByFirstNameContainingOrLastNameContainingOrMiddleNameContainingOrSpecialtyContaining(
                keyword, keyword, keyword, keyword);
    }


}

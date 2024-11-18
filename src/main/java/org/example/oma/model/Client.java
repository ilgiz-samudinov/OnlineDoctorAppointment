package org.example.oma.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="last_name" , nullable = false)
    private String lastName;

    @Column(name = "first_name",  nullable = false)
    private String firstName;

    @Column(name= "middle_name" )
    private String middleName;

    @Column(name= "phone", nullable = false, unique = true)
    private String phone;


    @OneToMany(mappedBy ="client" , cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Appointment> appointments;

    public Client(){}

    public Client(Long id, String lastName, String firstName, String middleName, String phone) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }


}

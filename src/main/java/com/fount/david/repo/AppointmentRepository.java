package com.fount.david.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fount.david.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}

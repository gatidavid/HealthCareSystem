package com.fount.david.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;

@Data
@Entity
@Table(name="slot_req_tab",
		uniqueConstraints={
			@UniqueConstraint(columnNames = {"app_id_fk_col","ptint_id_fk_col"})	
		})
public class SlotRequest {

	@Id
	@Autowired
	@Column(name="slot_id_col")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="app_id_fk_col")
	private Appointment appointment;

	@OneToOne
	@JoinColumn(name="ptint_id_fk_col")
	private Patient patient;

	@Column(name="slot_status_col")
	private String status;
	
}

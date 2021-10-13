package com.fount.david.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="patient_tab")
public class Patient {

	@Id
	@GeneratedValue
	@Column(name="pat_id_col")
	private Long id;
	
	@Column(name="pat_firstname_col")
	private String firstName;
	
	@Column(name="pat_lastname_col")
	private String lastName;
	
	@Column(name="pat_gender_col")
	private String gender;
	
	@Column(name="pat_mobile_col")
	private String mobile;
	
	@Column(name="pat_email_col")
	private String email;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name="pat_date_of_birth_col")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Column(name="pat_marital_status_col")
	private String maritalStatus;
	
	@Column(name="pat_present_addr_col")
	private String presentAddr;
	
	@Column(name="pat_comm_addr_col")
	private String commAddr;
	
	@ElementCollection
	@CollectionTable(
			name="pat_medi_hist_tab",
			joinColumns = @JoinColumn(name="pat_medi_hist_id_fk_col"))
	private Set<String> pastMedicalHistory;
	
	@Column(name="pat_other_med_hist")
	private String ifOther;
	
	@Column(name="pat_note_col")
	private String note;
	
	
}

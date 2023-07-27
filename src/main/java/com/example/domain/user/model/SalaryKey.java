package com.example.domain.user.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "t_salary")
public class SalaryKey {
	@Id
	private String userId;
	private String password;
	private String userName;
	private Date birthday;
	private Integer gender;
	private Integer departmentId;
	private Integer role;

	@ManyToOne(optional = true)
	@JoinColumn(insertable = false, updatable = false, name = "departmentId")
	private Department department;

	@OneToMany
	@JoinColumn(insertable = false, updatable = false, name = "userId")
	private List<Salary> slaryList;
}

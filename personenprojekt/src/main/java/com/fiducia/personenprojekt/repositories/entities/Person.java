package com.fiducia.personenprojekt.repositories.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tblpersonen")
@NamedQuery(name = "Person.findAllByNachname", query="select p from Person p where p.nachname like :nachname")
public class Person {
	@Id
	@Column(length = 36, nullable = false)
	private String id;
	@Column(length = 30)
	private String vorname;
	@Column(length = 30, nullable = false)
	private String nachname;

}

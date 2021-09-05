package it.uniroma3.siw.spring.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Insegnante {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long matricola;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String cognome;
	
	@Column(nullable = false)
	private String email;
	
	@OneToMany(mappedBy = "insegnante")
	private List<Lezione> lezioni;
	
	@ManyToMany(mappedBy = "insegnanti")
	private List<Corso> corsi;
	
	
}

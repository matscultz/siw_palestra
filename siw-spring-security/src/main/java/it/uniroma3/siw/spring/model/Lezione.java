package it.uniroma3.siw.spring.model;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class Lezione {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String nome;
	
	@Column(nullable = false)
	private int durata;  // int o string?
	
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate data;
	
	@Column(nullable = false)
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime orario;
	
	@ManyToOne
	private Insegnante insegnante;
	
	@ManyToOne
	private Corso corso;
		
	@OneToMany(mappedBy = "lezione", cascade = CascadeType.REMOVE)
	private List<Prenotazione> prenotazioni;
}

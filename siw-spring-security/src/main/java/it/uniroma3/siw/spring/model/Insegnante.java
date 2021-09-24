package it.uniroma3.siw.spring.model;

import java.beans.Transient;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Insegnante {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String cognome;
	
	@Column(nullable = false)
	private String email;
	
	@OneToMany(mappedBy = "insegnante")
	private List<Lezione> lezioni;
	
	
	 @Column(nullable = true, length = 64)
	    private String photos;
	
	 @Transient
	    public String getPhotosImagePath() {
	        if (photos == null || id == null) return null;
	         
	        return "/insegnante-photos/" + id + "/" + photos;
	    }
	
	/* @ManyToMany(mappedBy = "insegnanti")
	private List<Corso> corsi; */
	
	@ManyToOne
	private Corso corso;
}

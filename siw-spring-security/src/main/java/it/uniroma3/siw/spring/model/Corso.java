package it.uniroma3.siw.spring.model;

import java.beans.Transient;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Corso {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column
	private String descrizione;
	
	 @Column(nullable = true, length = 64)
	    private String photos;
	
	 @Transient
	    public String getPhotosImagePath() {
	        if (photos == null || id == null) return null;
	         
	        return "/corso-photos/" + id + "/" + photos;
	    }
	/* @ManyToMany
	private List<Insegnante> insegnanti; */
	
	@OneToMany(mappedBy="corso",cascade= {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private List<Insegnante> insegnanti;
	
	@OneToMany(mappedBy = "corso")
	private List<Lezione> lezioni;

	@Override
	public String toString() {
		return "Corso [id=" + id + ", nome=" + nome + ", descrizione=" + descrizione + ", photos=" + photos
				+ ", insegnanti=" + insegnanti + "]";
	}
	
	
}

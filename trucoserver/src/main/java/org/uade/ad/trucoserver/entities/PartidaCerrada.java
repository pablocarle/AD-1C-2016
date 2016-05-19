package org.uade.ad.trucoserver.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="partidas_cerradas")
@PrimaryKeyJoinColumn(name="idPartida")
public class PartidaCerrada extends Partida {

	@OneToOne
	@JoinColumn(name="idGrupo")
	private Grupo grupo;
	
	public PartidaCerrada() {
		super();
	}
	
	public PartidaCerrada(Grupo grupo) {
	}
}

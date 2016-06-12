package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Partida")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartidaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@XmlAttribute
	private int idPartida;
	@XmlElement
	private TipoPartidaDTO tipoPartida;
	@XmlElement
	private List<ChicoDTO> chicos;
	@XmlAttribute
	private String estado;
	@XmlElement
	private ParejaDTO pareja1;
	@XmlElement
	private ParejaDTO pareja2;
	@XmlElement(required=false)
	private GrupoDTO grupo;
	
	@XmlElement
	private JugadorDTO turnoAnterior;
	@XmlElement
	private JugadorDTO turnoActual;
	
	@XmlElement
	private List<EnviteDTO> turnoActualEnvidos = new ArrayList<>();
	@XmlElement
	private List<CartaDTO> turnoActualCartasDisponibles = new ArrayList<>();
	@XmlElement
	private List<EnviteDTO> turnoActualTrucos= new ArrayList<>();
	@XmlAttribute
	private boolean trucoEnCurso = false;
	@XmlAttribute
	private boolean envidoEnCurso = false;
	@XmlElement
	private List<CartasDisponiblesDTO> cartasDisponibles = new ArrayList<>();
	
	public PartidaDTO() {
		super();
	}
	
	public boolean esNull() {
		return idPartida == -1;
	}
	
	public ChicoDTO getChicoActual() {
		//TODO Obtener el chico actual
		return null;
	}

	public int getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
	}

	public TipoPartidaDTO getTipoPartida() {
		return tipoPartida;
	}

	public void setTipoPartida(TipoPartidaDTO tipoPartida) {
		this.tipoPartida = tipoPartida;
	}

	public List<ChicoDTO> getChicos() {
		return chicos;
	}

	public void setChicos(List<ChicoDTO> chicos) {
		this.chicos = chicos;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public ParejaDTO getPareja1() {
		return pareja1;
	}

	public void setPareja1(ParejaDTO pareja1) {
		this.pareja1 = pareja1;
	}

	public ParejaDTO getPareja2() {
		return pareja2;
	}

	public void setPareja2(ParejaDTO pareja2) {
		this.pareja2 = pareja2;
	}

	public JugadorDTO getTurnoAnterior() {
		return turnoAnterior;
	}

	public void setTurnoAnterior(JugadorDTO turnoAnterior) {
		this.turnoAnterior = turnoAnterior;
	}

	public JugadorDTO getTurnoActual() {
		return turnoActual;
	}

	public void setTurnoActual(JugadorDTO turnoActual) {
		this.turnoActual = turnoActual;
	}

	public void setGrupo(GrupoDTO grupo) {
		this.grupo = grupo;
	}
	
	public GrupoDTO getGrupo() {
		return grupo;
	}

	public List<EnviteDTO> getTurnoActualEnvidos() {
		return turnoActualEnvidos;
	}

	public void setTurnoActualEnvidos(List<EnviteDTO> turnoActualEnvidos) {
		this.turnoActualEnvidos = turnoActualEnvidos;
	}

	public List<CartaDTO> getTurnoActualCartasDisponibles() {
		return turnoActualCartasDisponibles;
	}

	public void setTurnoActualCartasDisponibles(List<CartaDTO> turnoActualCartasDisponibles) {
		this.turnoActualCartasDisponibles = turnoActualCartasDisponibles;
	}

	public List<EnviteDTO> getTurnoActualTrucos() {
		return turnoActualTrucos;
	}

	public void setTurnoActualTrucos(List<EnviteDTO> turnoActualTrucos) {
		this.turnoActualTrucos = turnoActualTrucos;
	}

	public boolean isTrucoEnCurso() {
		return trucoEnCurso;
	}

	public void setTrucoEnCurso(boolean trucoEnCurso) {
		this.trucoEnCurso = trucoEnCurso;
	}

	public boolean isEnvidoEnCurso() {
		return envidoEnCurso;
	}

	public void setEnvidoEnCurso(boolean envidoEnCurso) {
		this.envidoEnCurso = envidoEnCurso;
	}

	public List<CartasDisponiblesDTO> getCartasDisponibles() {
		return cartasDisponibles;
	}

	public void setCartasDisponibles(List<CartasDisponiblesDTO> cartasDisponibles) {
		this.cartasDisponibles = cartasDisponibles;
	}
}

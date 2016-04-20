package org.uade.ad.trucoserver;

/**
 * Modalidades de juego soportadas
 * 
 * Debe coincidir ID con los definidos en la tabla tipopartidas
 * 
 * @author Grupo9
 *
 */
public enum ModalidadJuego {
	
	ABIERTO_INDIVIDUAL(1),
	ABIERTO_PAREJA(2),
	CERRADO(3);
	
	private int id;

	private ModalidadJuego(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getStrId() {
		return String.valueOf(id);
	}
	
	public static ModalidadJuego parse(int id) {
		switch (id) {
			case 1: {
				return ABIERTO_INDIVIDUAL;
			}
			case 2: {
				return ABIERTO_PAREJA;
			}
			case 3: {
				return CERRADO;
			}
			default:
				throw new RuntimeException("No se identifica tipo de partida para id " + id);
		}
	}
}

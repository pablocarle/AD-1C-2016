package org.uade.ad.web.util;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.uade.ad.trucorepo.dtos.NotificacionDTO;
import org.uade.ad.trucorepo.dtos.NotificacionesDTO;
import org.uade.ad.trucorepo.dtos.PartidaDTO;

public class XMLSerialize {

	private static JAXBContext context;
	private static boolean initialized = false;
	
	private XMLSerialize() {
		super();
	}

	public static String serialize(PartidaDTO partida) throws JAXBException {
		Marshaller m = context.createMarshaller();
		StringWriter sw = new StringWriter();
		m.marshal(partida, sw);
		return sw.toString();
	}
	
	public static String serialize(NotificacionDTO notificacion) throws JAXBException {
		Marshaller m = context.createMarshaller();
		StringWriter sw = new StringWriter();
		m.marshal(notificacion, sw);
		return sw.toString();
	}
	
	public static String serialize(List<NotificacionDTO> listaNotificaciones) throws JAXBException {
		Marshaller m = context.createMarshaller();
		StringWriter sw = new StringWriter();
		m.marshal(listaNotificaciones, sw);
		return sw.toString();
	}

	public static void init() throws JAXBException {
		if (!initialized) {
			context = JAXBContext.newInstance(NotificacionesDTO.class, NotificacionDTO.class, PartidaDTO.class);
			initialized = true;
		}
	}

	public static String serialize(NotificacionesDTO dto) throws JAXBException {
		Marshaller m = context.createMarshaller();
		StringWriter sw = new StringWriter();
		m.marshal(dto, sw);
		return sw.toString();
	}
}

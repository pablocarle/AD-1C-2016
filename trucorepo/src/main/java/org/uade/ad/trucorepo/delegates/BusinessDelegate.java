package org.uade.ad.trucorepo.delegates;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class BusinessDelegate {

	protected static Properties webServerProperties;
	
	static {
		//TODO Completar lectura de properties
		InputStream is = null;
		try {
			is = BusinessDelegate.class.getClassLoader().getResourceAsStream("server.properties");
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public BusinessDelegate() {
	}

}

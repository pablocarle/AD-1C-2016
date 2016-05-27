package org.uade.ad.trucorepo.delegates;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class BusinessDelegate {

	protected static Properties webServerProperties;
	
	static {
		InputStream is = null;
		try {
			is = BusinessDelegate.class.getClassLoader().getResourceAsStream("server.properties");
			webServerProperties = new Properties();
			webServerProperties.load(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public BusinessDelegate() {
	}

}

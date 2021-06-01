package com.naturecloud.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadFile {
	ReadFile read;
	private String configPath=null;
	private Properties props=null;
	public ReadFile() {
		InputStream in=ReadFile.class.getResourceAsStream("/config/config.properties");
		props=new Properties();
			try {
				props.load(in);
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
	public String readValue(String key){
		return props.getProperty(key);
	}
}

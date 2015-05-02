/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author fasaloni
 */
public class PropertyProvider {

    private static PropertyProvider singletonInstance;
    private Properties properties;

    private PropertyProvider(String propFileName) {
        
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        try {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static PropertyProvider getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new PropertyProvider("config.properties");
        }
        return singletonInstance;
    }

    public String getProperty(String propName) {
        return properties.getProperty(propName);
    }
}

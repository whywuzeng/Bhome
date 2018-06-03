package com.system.bhouse.bhouse.phone.config;

import android.content.Context;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesConfig extends Properties {
    private static final long serialVersionUID = -6036468980128992800L;
    String configPath = "JQCAMConfig.properties";

    private boolean hasConfigFile() {
        return false;
    }

    public Properties loadConfig(Context context) {
        Properties properties = new Properties();
        try {
            InputStream openFileInput = context.openFileInput(this.configPath);
            properties.load(openFileInput);
            openFileInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    public void saveProperty(Context context, String str, String str2) {
        Properties loadConfig = loadConfig(context);
        loadConfig.setProperty(str, str2);
        try {
            OutputStream openFileOutput = context.openFileOutput(this.configPath, 0);
            loadConfig.store(openFileOutput, "");
            openFileOutput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}

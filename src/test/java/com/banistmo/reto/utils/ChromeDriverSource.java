package com.banistmo.reto.utils;

import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Crea un Chrome configurado para DESCARGAR los PDF (en vez de abrirlos en el visor)
 * dentro de build/downloads, de modo que luego se pueda validar su contenido.
 * Selenium Manager (incluido en Selenium 4) resuelve el chromedriver automáticamente.
 */
public class ChromeDriverSource implements DriverSource {

    @Override
    public WebDriver newDriver() {
        DownloadDirectory.prepareClean();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", DownloadDirectory.path().toString());
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.directory_upgrade", true);
        prefs.put("plugins.always_open_pdf_externally", true);
        prefs.put("profile.default_content_settings.popups", 0);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--start-maximized", "--disable-notifications",
                "--disable-popup-blocking", "--remote-allow-origins=*", "--lang=es-PA");
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }
        return new ChromeDriver(options);
    }

    @Override
    public boolean takesScreenshots() {
        return true;
    }
}

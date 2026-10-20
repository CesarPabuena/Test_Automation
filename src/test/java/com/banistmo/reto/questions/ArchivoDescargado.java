package com.banistmo.reto.questions;

import com.banistmo.reto.utils.DownloadDirectory;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/** Archivo PDF descargado y utilidades para validar su firma "%PDF-". */
public class ArchivoDescargado implements Question<File> {

    public static ArchivoDescargado pdf() {
        return new ArchivoDescargado();
    }

    public static boolean tieneFirmaPdf(File archivo) {
        try (InputStream in = Files.newInputStream(archivo.toPath())) {
            byte[] cabecera = in.readNBytes(5);
            return cabecera.length == 5 && new String(cabecera, StandardCharsets.US_ASCII).equals("%PDF-");
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public File answeredBy(Actor actor) {
        return DownloadDirectory.lastDownloadedPdf()
                .orElseThrow(() -> new AssertionError("No hay ningún PDF descargado en " + DownloadDirectory.path()));
    }
}

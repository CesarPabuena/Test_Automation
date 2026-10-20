package com.banistmo.reto.questions;

import com.banistmo.reto.utils.DownloadDirectory;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Locale;

/** Lee el PDF descargado y retorna su texto normalizado (mayúsculas, sin tildes ni espacios múltiples). */
public class ContenidoDelPdf implements Question<String> {

    public static ContenidoDelPdf descargado() {
        return new ContenidoDelPdf();
    }

    public static String normalizar(String texto) {
        String sinTildes = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return sinTildes.toUpperCase(Locale.ROOT).replaceAll("[\\s\\u00A0]+", " ").trim();
    }

    @Override
    public String answeredBy(Actor actor) {
        File pdf = DownloadDirectory.lastDownloadedPdf()
                .orElseThrow(() -> new AssertionError("No hay ningún PDF descargado en " + DownloadDirectory.path()));
        try (PDDocument documento = Loader.loadPDF(pdf)) {
            return normalizar(new PDFTextStripper().getText(documento));
        } catch (IOException e) {
            throw new AssertionError("No fue posible leer el PDF descargado: " + pdf, e);
        }
    }
}

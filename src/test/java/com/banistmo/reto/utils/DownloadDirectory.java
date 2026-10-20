package com.banistmo.reto.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

/** Carpeta donde Chrome deja los archivos descargados y utilidades para esperarlos. */
public final class DownloadDirectory {

    private DownloadDirectory() {
    }

    public static Path path() {
        String configured = System.getProperty("download.dir", "build/downloads");
        return Paths.get(configured).toAbsolutePath();
    }

    /** Crea la carpeta si no existe y elimina archivos de ejecuciones anteriores. */
    public static void prepareClean() {
        try {
            Files.createDirectories(path());
            try (Stream<Path> files = Files.list(path())) {
                files.filter(Files::isRegularFile).map(Path::toFile).forEach(File::delete);
            }
        } catch (IOException e) {
            throw new IllegalStateException("No fue posible preparar la carpeta de descargas: " + path(), e);
        }
    }

    /** Espera a que aparezca un PDF completo (sin .crdownload / .tmp) y lo retorna. */
    public static Optional<File> waitForPdf(int timeoutSeconds) {
        long limit = System.currentTimeMillis() + timeoutSeconds * 1000L;
        do {
            try (Stream<Path> files = Files.list(path())) {
                Optional<File> pdf = files
                        .map(Path::toFile)
                        .filter(f -> f.isFile() && f.getName().toLowerCase().endsWith(".pdf") && f.length() > 0)
                        .findFirst();
                if (pdf.isPresent()) {
                    return pdf;
                }
            } catch (IOException ignored) {
                // se reintenta
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        } while (System.currentTimeMillis() < limit);
        return Optional.empty();
    }

    public static Optional<File> lastDownloadedPdf() {
        return waitForPdf(1);
    }
}

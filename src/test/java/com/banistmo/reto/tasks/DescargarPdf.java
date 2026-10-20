package com.banistmo.reto.tasks;

import com.banistmo.reto.interactions.ClickEnElPrimeroVisible;
import com.banistmo.reto.userinterfaces.BanistmoUI;
import com.banistmo.reto.utils.DownloadDirectory;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/** Hace click en el PDF indicado y espera a que termine la descarga en la carpeta configurada. */
public class DescargarPdf implements Task {

    private static final int TIMEOUT_DESCARGA_SEGUNDOS = 45;
    private final String nombreDocumento;

    public DescargarPdf(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public static DescargarPdf delDocumento(String nombreDocumento) {
        return instrumented(DescargarPdf.class, nombreDocumento);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        DownloadDirectory.prepareClean();
        actor.attemptsTo(ClickEnElPrimeroVisible.en(BanistmoUI.ENLACE_PDF.of(nombreDocumento)));
        DownloadDirectory.waitForPdf(TIMEOUT_DESCARGA_SEGUNDOS)
                .orElseThrow(() -> new AssertionError(
                        "El PDF no se descargó en " + TIMEOUT_DESCARGA_SEGUNDOS + "s en " + DownloadDirectory.path()));
    }
}

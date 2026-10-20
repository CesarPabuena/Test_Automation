package com.banistmo.reto.interactions;

import com.banistmo.reto.userinterfaces.BanistmoUI;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.JavascriptExecutor;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/** Cierra (si aparecen) el aviso de privacidad y el popup de MultiPuntos para evitar clicks interceptados. */
public class CerrarAvisos implements Interaction {

    public static CerrarAvisos emergentes() {
        return instrumented(CerrarAvisos.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        for (Target aviso : new Target[]{BanistmoUI.BOTON_ENTENDIDO, BanistmoUI.CHECK_NO_MOSTRAR_POPUP}) {
            try {
                for (WebElementFacade el : aviso.resolveAllFor(actor)) {
                    if (el.isCurrentlyVisible()) {
                        ((JavascriptExecutor) el.getDriver()).executeScript("arguments[0].click();", el);
                        Thread.sleep(300);
                    }
                }
            } catch (Exception ignored) {
                // los avisos son opcionales: si no aparecen o cambian, el flujo continúa
            }
        }
    }
}

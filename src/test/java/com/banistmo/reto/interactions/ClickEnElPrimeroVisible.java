package com.banistmo.reto.interactions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.JavascriptExecutor;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Hace scroll y click sobre el primer elemento visible del target. Útil porque el sitio duplica
 * componentes (desktop/mobile) y porque el click normal puede ser interceptado por banners.
 */
public class ClickEnElPrimeroVisible implements Interaction {

    private static final long TIMEOUT_MS = 20_000;
    private final Target target;

    public ClickEnElPrimeroVisible(Target target) {
        this.target = target;
    }

    public static ClickEnElPrimeroVisible en(Target target) {
        return instrumented(ClickEnElPrimeroVisible.class, target);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        long limite = System.currentTimeMillis() + TIMEOUT_MS;
        WebElementFacade elemento = null;
        while (System.currentTimeMillis() < limite && elemento == null) {
            List<WebElementFacade> candidatos = target.resolveAllFor(actor);
            for (WebElementFacade candidato : candidatos) {
                if (candidato.isCurrentlyVisible()) {
                    elemento = candidato;
                    break;
                }
            }
            if (elemento == null) {
                pausa(500);
            }
        }
        if (elemento == null) {
            throw new AssertionError("No se encontró un elemento visible para: " + target.getName());
        }
        JavascriptExecutor js = (JavascriptExecutor) elemento.getDriver();
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", elemento);
        pausa(400);
        try {
            elemento.click();
        } catch (Exception e) {
            // Si un banner intercepta el click, se hace click por JavaScript
            js.executeScript("arguments[0].click();", elemento);
        }
    }

    private static void pausa(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

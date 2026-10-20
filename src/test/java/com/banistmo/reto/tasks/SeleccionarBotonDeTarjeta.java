package com.banistmo.reto.tasks;

import com.banistmo.reto.interactions.ClickEnElPrimeroVisible;
import com.banistmo.reto.userinterfaces.BanistmoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/** Hace click en el botón (p. ej. "Descubre más") de la tarjeta con el título indicado. */
public class SeleccionarBotonDeTarjeta implements Task {

    private final String boton;
    private final String tarjeta;

    public SeleccionarBotonDeTarjeta(String boton, String tarjeta) {
        this.boton = boton;
        this.tarjeta = tarjeta;
    }

    public static SeleccionarBotonDeTarjeta llamado(String boton, String tarjeta) {
        return instrumented(SeleccionarBotonDeTarjeta.class, boton, tarjeta);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(ClickEnElPrimeroVisible.en(BanistmoUI.BOTON_DE_TARJETA.of(tarjeta, boton)));
    }
}

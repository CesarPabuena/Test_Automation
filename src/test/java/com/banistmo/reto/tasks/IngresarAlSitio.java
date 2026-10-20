package com.banistmo.reto.tasks;

import com.banistmo.reto.interactions.CerrarAvisos;
import com.banistmo.reto.userinterfaces.BanistmoPersonasPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class IngresarAlSitio implements Task {

    public static IngresarAlSitio deBanistmoPersonas() {
        return instrumented(IngresarAlSitio.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.browserOn().the(BanistmoPersonasPage.class),
                CerrarAvisos.emergentes());
    }
}

package com.banistmo.reto.tasks;

import com.banistmo.reto.interactions.CerrarAvisos;
import com.banistmo.reto.interactions.ClickEnElPrimeroVisible;
import com.banistmo.reto.userinterfaces.BanistmoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class IrAOpcionDelPie implements Task {

    private final String opcion;

    public IrAOpcionDelPie(String opcion) {
        this.opcion = opcion;
    }

    public static IrAOpcionDelPie llamada(String opcion) {
        return instrumented(IrAOpcionDelPie.class, opcion);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                CerrarAvisos.emergentes(),
                ClickEnElPrimeroVisible.en(BanistmoUI.ENLACE_PIE_DE_PAGINA.of(opcion)));
    }
}

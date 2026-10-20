package com.banistmo.reto.stepdefinitions;

import com.banistmo.reto.questions.ArchivoDescargado;
import com.banistmo.reto.questions.ContenidoDelPdf;
import com.banistmo.reto.tasks.DescargarPdf;
import com.banistmo.reto.tasks.IngresarAlSitio;
import com.banistmo.reto.tasks.IrAOpcionDelPie;
import com.banistmo.reto.tasks.SeleccionarBotonDeTarjeta;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

import java.io.File;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;

public class DescargaAutocertificacionStepDefinitions {

    @Dado("que el usuario ingresa a la página de personas de Banistmo")
    public void ingresaALaPagina() {
        theActorCalled("Usuario").wasAbleTo(IngresarAlSitio.deBanistmoPersonas());
    }

    @Cuando("ingresa a la opción {string} del pie de página")
    public void ingresaAOpcionDelPie(String opcion) {
        theActorInTheSpotlight().attemptsTo(IrAOpcionDelPie.llamada(opcion));
    }

    @Cuando("selecciona la opción {string} del apartado {string}")
    public void seleccionaOpcionDelApartado(String boton, String apartado) {
        theActorInTheSpotlight().attemptsTo(SeleccionarBotonDeTarjeta.llamado(boton, apartado));
    }

    @Cuando("ingresa a la opción {string} del apartado {string}")
    public void ingresaAOpcionDelApartado(String boton, String apartado) {
        theActorInTheSpotlight().attemptsTo(SeleccionarBotonDeTarjeta.llamado(boton, apartado));
    }

    @Cuando("descarga el PDF del documento {string}")
    public void descargaElPdf(String documento) {
        theActorInTheSpotlight().attemptsTo(DescargarPdf.delDocumento(documento));
    }

    @Entonces("el documento descargado es la autocertificación fiscal de persona jurídica")
    public void validaElPdf() {
        File pdf = theActorInTheSpotlight().asksFor(ArchivoDescargado.pdf());
        String contenido = theActorInTheSpotlight().asksFor(ContenidoDelPdf.descargado());

        // 1. Validaciones técnicas del archivo
        assertThat(pdf).as("el archivo descargado existe").exists();
        assertThat(pdf.length()).as("el PDF no está vacío").isGreaterThan(0);
        assertThat(ArchivoDescargado.tieneFirmaPdf(pdf)).as("el archivo tiene cabecera %PDF-").isTrue();

        // 2. Validaciones de contenido: es el formulario de PERSONA JURÍDICA, FATCA y CRS
        assertThat(contenido)
                .as("título y secciones del formulario")
                .contains(ContenidoDelPdf.normalizar("AUTO-CERTIFICACIÓN FISCAL"))
                .contains(ContenidoDelPdf.normalizar("PERSONA JURÍDICA"))
                .contains(ContenidoDelPdf.normalizar("INFORMACIÓN GENERAL DEL CUENTAHABIENTE"))
                .contains(ContenidoDelPdf.normalizar("AUTOCERTIFICACIÓN FISCAL FATCA"));

        // 3. No es el formulario de persona natural (PN)
        assertThat(contenido).as("no es el documento de persona natural").doesNotContain("PERSONA NATURAL");
    }
}

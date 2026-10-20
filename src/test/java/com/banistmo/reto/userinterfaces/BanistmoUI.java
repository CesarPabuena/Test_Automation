package com.banistmo.reto.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

/** Localizadores del flujo (XPath basados en texto visible para que sean estables). */
public final class BanistmoUI {

    private BanistmoUI() {
    }

    /** Enlace del pie de página (hay versiones desktop y mobile; se usa el primer visible). */
    public static final Target ENLACE_PIE_DE_PAGINA = Target
            .the("enlace '{0}' del pie de página")
            .locatedBy("//a[normalize-space(.)='{0}' and contains(@href,'aprender-es-facil')]");

    /** Botón "Descubre más"/"Conoce más" ({1}) de la tarjeta cuyo título es {0}. */
    public static final Target BOTON_DE_TARJETA = Target
            .the("botón '{1}' de la tarjeta '{0}'")
            .locatedBy("(//*[self::h2 or self::h3 or self::h4]"
                    + "[contains(normalize-space(.),'{0}')]"
                    + "/following::*[self::a or self::button]"
                    + "[contains(normalize-space(.),'{1}')])[1]");

    /** Enlace/botón PDF dentro de "Documentos para descargar". */
    public static final Target ENLACE_PDF = Target
            .the("enlace del PDF '{0}'")
            .locatedBy("//a[contains(normalize-space(.),'{0}') and contains(normalize-space(.),'.pdf')]");

    public static final Target BOTON_ENTENDIDO = Target
            .the("botón 'Entendido' del aviso de privacidad")
            .locatedBy("//button[normalize-space(.)='Entendido']");

    public static final Target CHECK_NO_MOSTRAR_POPUP = Target
            .the("opción para no volver a mostrar el popup")
            .locatedBy("//*[normalize-space(.)='No volver a mostrar este mensaje']");
}

# Reto técnico de Automatización – Skill Hacking Banistmo

Automatización web con **Java 17 + Gradle + Serenity BDD (Screenplay) + Cucumber (Gherkin en español)**.

## Flujo automatizado
1. Abre https://www.banistmo.com/personas
2. Pie de página → **Aprender es fácil**
3. Apartado **Legales** → **Descubre más**
4. Apartado **FATCA & CRS** → **Descubre más**
5. Descarga el PDF **PJ-Autocertificación unificado - Fatca y CRS.pdf**
6. Valida (con Apache PDFBox) que el PDF descargado es el formulario *Auto-certificación fiscal – Persona Jurídica*.

## Requisitos
- JDK 17+ y Google Chrome instalado (Selenium Manager descarga el chromedriver).
- Gradle 8.x (o generar el wrapper una vez con `gradle wrapper --gradle-version 8.7`).

## Ejecución
```bash
gradle clean test                    # abre Chrome
gradle clean test -Pheadless=true    # sin interfaz (útil en CI)
```
Reporte Serenity: `target/site/serenity/index.html`. PDF descargado: `build/downloads/`.

## Estructura (patrón Screenplay)
```
src/test/java/com/banistmo/reto
├── runners/          CucumberTestSuite
├── hooks/            Escenario (OnStage / OnlineCast)
├── stepdefinitions/  DescargaAutocertificacionStepDefinitions
├── tasks/            IngresarAlSitio, IrAOpcionDelPie, SeleccionarBotonDeTarjeta, DescargarPdf
├── interactions/     ClickEnElPrimeroVisible, CerrarAvisos
├── userinterfaces/   BanistmoPersonasPage, BanistmoUI (Targets)
├── questions/        ArchivoDescargado, ContenidoDelPdf
└── utils/            ChromeDriverSource, DownloadDirectory
src/test/resources/features/descarga_autocertificacion.feature
```

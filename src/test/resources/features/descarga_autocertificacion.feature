# language: es
@reto @descarga_pdf
Característica: Descarga de la Autocertificación FATCA & CRS de Persona Jurídica
  Como usuario del sitio web de Banistmo
  Quiero descargar el formulario de autocertificación unificado FATCA y CRS
  Para validar que el documento obtenido es el correcto

  Escenario: Descargar y validar el PDF "PJ-Autocertificación unificado - Fatca y CRS"
    Dado que el usuario ingresa a la página de personas de Banistmo
    Cuando ingresa a la opción "Aprender es fácil" del pie de página
    Y selecciona la opción "Descubre más" del apartado "Legales"
    Y ingresa a la opción "Descubre más" del apartado "FATCA & CRS"
    Y descarga el PDF del documento "PJ-Autocertificación unificado"
    Entonces el documento descargado es la autocertificación fiscal de persona jurídica

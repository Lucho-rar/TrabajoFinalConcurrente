import re

# *** Se agregan los invariantes ya que post-correciones no se cuentan en las ejecuciones en Java.
# Lista de invariantes
invariantes = {
    "T0T1T3T5T7T9T11T13T14": 0,
    "T0T1T3T5T7T10T12T13T14": 0,
    "T0T1T3T6T8T9T11T13T14": 0,
    "T0T1T3T6T8T10T12T13T14": 0,
    "T0T2T4T5T7T9T11T13T14": 0,
    "T0T2T4T5T7T10T12T13T14": 0,
    "T0T2T4T6T8T9T11T13T14": 0,
    "T0T2T4T6T8T10T12T13T14": 0
}

# Se separa la regex para más claridad, siguiendo el patrón de la regex original
regex = (
    r"(?P<S0>T0)(?P<PT0>(?:T\d+)*?)"
    r"(?:(?P<S1>T1)(?P<PT1>(?:T\d+)*?)(?P<S3>T3)|(?P<S2>T2)(?P<PT2>(?:T\d+)*?)(?P<S4>T4))"
    r"(?P<PT3T4>(?:T\d+)*?)"
    r"(?:(?P<S5>T5)(?P<PT5>(?:T\d+)*?)(?P<S7>T7)|(?P<S6>T6)(?P<PT6>(?:T\d+)*?)(?P<S8>T8))"
    r"(?P<PT7T8>(?:T\d+)*?)"
    r"(?:(?P<S10>T10)(?P<PT10>(?:T\d+)*?)(?P<S12>T12)|(?P<S9>T9)(?P<PT9>(?:T\d+)*?)(?P<S11>T11))"
    r"(?P<PT11T12>(?:T\d+)*?)(?P<S13>T13)(?P<PT13>(?:T\d+)*?)(?P<S14>T14)(?P<PT14>(?:T\d+)*?)"
)

# Patrón de grupos para reemplazo
grupos = (
    "#\\g<S0>\\g<S1>\\g<S3>\\g<S2>\\g<S4>\\g<S5>\\g<S7>\\g<S6>\\g<S8>\\g<S10>\\g<S12>\\g<S9>\\g<S11>\\g<S13>\\g<S14>"
    "#\\g<PT0>\\g<PT1>\\g<PT2>\\g<PT3T4>\\g<PT5>\\g<PT6>\\g<PT7T8>\\g<PT9>\\g<PT10>\\g<PT11T12>\\g<PT13>\\g<PT14>"
)

# Funcion anterior para procesar el archivo 
def procesar_archivo(archivo):
    """
    Procesa el archivo de log para validar transiciones y contar invariantes.
    """
    linea = archivo.readline().strip()
    resultado = re.subn(regex, grupos, linea)

    while resultado[1] > 0: 
        transiciones_restantes = contar_invariantes(resultado[0])
        resultado = re.subn(regex, grupos, transiciones_restantes)

    # Validar el resultado final
    if resultado[0]:
        print("ERROR EN LA VALIDACIÓN DE INVARIANTES PARA: ", archivo.name)
    else:
        print("LAS TRANSICIONES DE LOS INVARIANTES CORRECTAS PARA: ", archivo.name)

    # Mostrar el conteo de invariantes
    print("\nContador de invariantes:")
    for i, (invariante, conteo) in enumerate(invariantes.items(), start=1):
        print(f"El invariante {i} se completó: {conteo} veces.")

    print(f"\nSegmento A(T1-T3): {invariantes_por_indice(0)[1] + invariantes_por_indice(1)[1] + invariantes_por_indice(2)[1] + invariantes_por_indice(3)[1]}")
    print(f"Segmento B(T2-T4): {invariantes_por_indice(4)[1] + invariantes_por_indice(5)[1] + invariantes_por_indice(6)[1] + invariantes_por_indice(7)[1]}")
    print(f"Segmento C(T5-T7): {invariantes_por_indice(0)[1] + invariantes_por_indice(1)[1] + invariantes_por_indice(4)[1] + invariantes_por_indice(5)[1]}")
    print(f"Segmento D(T6-T8): {invariantes_por_indice(2)[1] + invariantes_por_indice(3)[1] + invariantes_por_indice(6)[1] + invariantes_por_indice(7)[1]}")
    print(f"Segmento E(T9-T11): {invariantes_por_indice(0)[1] + invariantes_por_indice(2)[1] + invariantes_por_indice(4)[1] + invariantes_por_indice(6)[1]}")
    print(f"Segmento F(T10-T12): {invariantes_por_indice(1)[1] + invariantes_por_indice(3)[1] + invariantes_por_indice(5)[1] + invariantes_por_indice(7)[1]}")
    print(f"Segmento G(T13-T14): {invariantes_por_indice(0)[1] + invariantes_por_indice(1)[1] +invariantes_por_indice(2)[1] + invariantes_por_indice(3)[1] + invariantes_por_indice(4)[1] + invariantes_por_indice(5)[1] + invariantes_por_indice(6)[1] + invariantes_por_indice(7)[1]}")

def invariantes_por_indice(indice):
    """
    Devuelve el invariante correspondiente al índice.
    """
    return list(invariantes.items())[indice]
def contar_invariantes(resultado):
    """
    Procesa el resultado de la sustitución para contar invariantes y devolver transiciones no coincidentes.
    """
    transiciones = resultado.split("#")
    transiciones_restantes = ""

    for transicion in transiciones:
        if not transicion:
            continue
        if transicion in invariantes:
            invariantes[transicion] += 1
        else:
            transiciones_restantes += transicion

    return transiciones_restantes

# Ejecutar el procesamiento del archivo
if __name__ == "__main__":
    nombre_archivo = "log_regex1.txt"  # Archivo de registro
    with open(nombre_archivo, "r") as archivo_log:
        procesar_archivo(archivo_log)
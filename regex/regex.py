import fileinput
import re

# Función para procesar usando expresiones regulares
def procesar_archivo(archivo):
    linea_actual = [archivo.readline(), 0]  # Leer la primera línea
    while True:  # Bucle de procesamiento
        # Aplicar la expresión regular y realizar reemplazos
        resultado = re.subn(
            r'T0((?:T(?:\d+))*?)(?:(?:T1((?:T(?:\d+))*?)T3)|(?:T2((?:T(?:\d+))*?)T4))((?:T(?:\d+))*?)'
            r'(?:(?:T5((?:T(?:\d+))*?)T7)|(?:T6((?:T(?:\d+))*?)T8))((?:T(?:\d+))*?)'
            r'(?:(?:T10((?:T(?:\d+))*?)T12)|(?:T9((?:T(?:\d+))*?)T11))((?:T(?:\d+))*?)T13((?:T(?:\d+))*?)T14',
            r'\g<1>\g<2>\g<3>\g<4>\g<5>\g<6>\g<7>\g<8>\g<9>\g<10>\g<11>',
            linea_actual[0].strip()
        )
        linea_actual = resultado

        if linea_actual[1] == 0:  # Si no hubo cambios, salir del bucle
            break

    # Verificar el resultado
    if linea_actual[0] == "":
        print("PROCES COOMPLETADO EXITOSAMENTE")
    else:
        print("OCURRIÓ UN ERROR")

# Abrir el archivo de entrada
nombre_archivo = "regex1.txt"  # Archivo de registro
with open(nombre_archivo, "r") as archivo_log:
    procesar_archivo(archivo_log)

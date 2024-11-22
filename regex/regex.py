import fileinput
import re

# funcion de regex
def regex(file ):
    line = [file.readline() , 0]
    while(True):        ## bucle de busqueda
        line = re.subn('T0((?:T(?:\d+))*?)(?:(?:T1((?:T(?:\d+))*?)T3)|(?:T2((?:T(?:\d+))*?)T4))((?:T(?:\d+))*?)(?:(?:T5((?:T(?:\d+))*?)T7)|(?:T6((?:T(?:\d+))*?)T8))((?:T(?:\d+))*?)(?:(?:T10((?:T(?:\d+))*?)T12)|(?:T9((?:T(?:\d+))*?)T11))((?:T(?:\d+))*?)T13((?:T(?:\d+))*?)T14', '\g<1>\g<2>\g<3>\g<4>\g<5>\g<6>\g<7>\g<8>\g<9>\g<10>\g<11>', line[0].rstrip())
        if(line[1] == 0):
            break

    print(line)
    if(line[0] == ""):      # comprobacion de resultado
        print("OK")
    else:
        print("FAIL")

file = open("invd.txt" , "r")   # setear nombre de log
regex(file)

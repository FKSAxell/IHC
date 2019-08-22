import pandas as pd

df = pd.read_excel('reporte_colaborativo.xlsx')
nombreA=df.columns
#print(nombreA[0])
datos=df.iloc[1:,:]
datos.columns =["Responsable","Version","Fecha","Cambios","VersionA"]
#print(datos)
#print(datos["Fecha"])
f = open("k.txt", "a",encoding='utf-8')
for x in range(datos.shape[0]):
    f.write(datos.iloc[x, :]["Responsable"])
    f.write(" | ")
    f.write(str(datos.iloc[x, :]["Version"]))
    f.write(" | ")
    f.write(datos.iloc[x, :]["Fecha"])
    f.write(" | ")
    f.write(str(datos.iloc[x, :]["Cambios"]))
    f.write(" | ")
    f.write(str(datos.iloc[x,:]["VersionA"]))
    f.write("\n")
f.close()

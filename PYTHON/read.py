import pandas as pd

df = pd.read_excel('reporte_colaborativo.xlsx')
nombreA=df.columns
print(nombreA[0])
datos=df.iloc[1:,:]
datos.columns =["Responsable","Version","Fecha","Cambios","VersionA"]
print(datos)
print(datos["Fecha"])

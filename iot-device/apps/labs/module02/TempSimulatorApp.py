'''
Created on 2018年9月15日

@author: howson
'''



from time import sleep
from labs.module02 import TempSensorEmulator
#from labs.module02 import SmtpClientConnector


tempSensEmulator = TempSensorEmulator.TempSensorEmulator()

tempSensEmulator.daemon = True
print("the app is running...")
tempSensEmulator.enableEmulator = True
tempSensEmulator.start()

while (True):
    sleep(10)
    pass
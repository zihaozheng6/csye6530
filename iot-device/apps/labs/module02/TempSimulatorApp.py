'''
Created on 2018年9月29日

@author: Howson
'''
from time import sleep
from labs.module02 import TempSensorEmulator
#from labs.module02 import SmtpClientConnector


tempSensEmulator = TempSensorEmulator.TempSensorEmulator()

tempSensEmulator.daemon = True
print('- - - - - - - - - - - - - - - - - - - - - - - - ')
print("Starting system performance app daemon thread...")
tempSensEmulator.enableEmulator = True
tempSensEmulator.start()

while (True):
    sleep(10)
    pass
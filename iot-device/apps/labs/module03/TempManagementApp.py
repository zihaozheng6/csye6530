'''
Created on 2018年9月15日

@author: howson
'''
import os,sys
sys.path.append('/home/pi/Desktop/howson/iot-device/apps')
from time           import sleep
from labs.module03 import TempSensorAdaptor

sysPerfAdaptor = TempSensorAdaptor.TempSensorAdaptor(True,0,30,0,True)

print("the TempManageApp is running .../n")

#sysPerfAdaptor.setEnableAdaptorFlag(True)
sysPerfAdaptor.start()

while (True):
    sleep(5)
    pass
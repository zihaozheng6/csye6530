'''
Created on Sep 28, 2018

@author: howson
'''
import os,sys
sys.path.append('/home/pi/Desktop/howson/iot-device/apps')
from labs.common.ActuatorData import ActuatorData
from labs.module03.SenseHatLedActivator import SenseHatLedActivator
from labs.module03.SimpleLedActivator import SimpleLedActivator

class TempActuatorEmulator():
    actuatorData = None
    senseHatLedActivator = None
    simpleLedActivator = None



    def __init__(self):
        '''
        Constructor
        '''
        self.actuatorData = ActuatorData()
        self.senseHatLedActivator = SenseHatLedActivator()
        self.simpleLedActivator = SimpleLedActivator()
        
    def processMessage(self, ActuatorData):
        self.actuatorData.updateData(ActuatorData)
        self.simpleLedActivator.setEnableLedFlag(True)
        if self.actuatorData.getCommand() == 0:
            message = "Temperature need to raise " + str(abs(self.actuatorData.getValue()))
        if self.actuatorData.getCommand() == 1:
            message = "Temperature need to lower " + str(self.actuatorData.getValue())
        self.senseHatLedActivator.setEnableLedFlag(True)
        self.senseHatLedActivator.setDisplayMessage(message)
        self.senseHatLedActivator.run()
'''
Created on Sep 28, 2018

@author: howson
'''
import os,sys
from sense_hat import SenseHat
import sense_hat
sys.path.append('/home/pi/Desktop/howson/iot-device/apps')
import threading
from time import sleep
from random import uniform
from sensorData import SensorData

import ConfigUtil
import ConfigConst


class SensorAdaptor(threading.Thread):
    '''
    this class is to create the Sensor from  the sense hat or by simulation. Provide the Api to get 
    the sensor data and calculate the average value of sensor data.
    :param enableEmulator: decide whether to get the sensor data from sense hat or create randomly.
    :param lowVal: the lowest value that create randomly.
    :param highVal: the highest value that create randomly.
    :param curTemp: the current time sensor data.
    :param isPrevTemSet: decide whether the previous data has been update.
    '''
    sh=None
    actuatorData = None
    tempActuatorEmulator = None
    
    sensorData = SensorData("Sensor") 
    alertDiff = 5
    def __init__(self, enableEmulator, lowVal, highVal, curTemp, isPrevTempSet):
        
        super(SensorAdaptor, self).__init__()
        self.enableEmulator = enableEmulator
        self.curTemp = curTemp
        self.lowVal = lowVal
        self.highVal = highVal
        self.isPrevTempSet = isPrevTempSet
        
        self.sh = sense_hat.SenseHat()
        
        self.config = ConfigUtil.ConfigUtil('')
        self.config.loadConfig()
        
       
        
    def getCurrentTemp(self):
        return self.currentTemp
    
    def getCurrentSensorData(self): 
        self.currentSensorData.addNewValue(self.currentTemp) # refresh SensorData only when it is called.
        return self.currentSensorData
    def run(self):
        nominalTemp = int(self.config.getProperty(ConfigConst.CONSTRAINED_DEVICE, ConfigConst.NOMINAL_TEMP_KEY))
        while True:
            if self.enableEmulator:
                self.curTemp = uniform(float(self.lowVal), float(self.highVal)) #create data randomly
                
                self.sensorData.addValue(self.curTemp)
                if self.isPrevTempSet == False:
 
                    self.prevTemp = self.curTemp
                    self.isPrevTempSet = True 
#                 
            else:
                self.curTemp = self.sh.get_temperature()
                self.sensorData.addValue(self.curTemp)

        sleep(1)    
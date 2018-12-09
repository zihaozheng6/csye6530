'''
Created on 2018年9月15日

@author: howson
'''
import os,sys
from builtins import str
sys.path.append('/home/pi/Desktop/howson/iot-device/apps')
from time           import sleep
from SensorAdaptor import SensorAdaptor
from threading import Thread
from time import sleep
import ConfigUtil
import ConfigConst
import mqttClientConnector
import sense_hat




class DeviceApp(Thread):
    '''
    this class is to run the constrained device. To get the sensor data from the sense hat or simulation.
    To make MQTT connection and publish or subscribe the topic from gateway.
    '''
    

    def __init__(self):
        ''' this to create the instance of mqttclientconnector and connect to the host we want'''
        Thread.__init__(self) 
        self.mqttClient=mqttClientConnector.MqttClientConnector("52.207.33.252",1883,on_connect, on_message, on_publish, on_subscribe)
        self.mqttClient.connect()
        self.config = ConfigUtil.ConfigUtil('')
        self.config.loadConfig()                                                                    
        self.pollCycleSecs= int(self.config.getProperty(ConfigConst.CONSTRAINED_DEVICE, ConfigConst.POLL_CYCLES_KEY))                  
        
    
    def run(self):
        '''this is to subscribe the topic and publish the sensor data got from the SensorAdaptor '''
        while True:              
            self.newSensorData=self.SensorAdaptor.getCurrentSensorData()
            self.mqttClient.subscribeTopic("iot/actautor", 2)
            self.mqttClient.subscribeTopic("iot/act", 2)
            self.mqttClient.publishMessage("iot/sensor",str(self.newSensorData.toDict()),2)
            sleep(self.pollCycleSecs) 
            
 
print("the DeviceApp is running .../n")                      
#start to simulate the Sensor data or get it from sensorhat.
sysAdaptor = SensorAdaptor(True,0,30,0,True)
sysAdaptor.start()
app = DeviceApp()
app.start()

while (True):
    sleep(5)
    pass
'''
Created on Sep 15, 2018

@author: Howson
'''
from datetime import datetime
import json
from src.json2xml import Json2xml

class SensorData:
    '''
    this class is to define the data we want, and complete the Api which is to change data  
    '''
    startedTime=None
    timeData=None
    type="No set"
    currValue=0.0
    avgValue=0.0
    minValue=0.0
    maxValue=0.0
    totalValue=0
    count=0
        
    def __init__(self,name):
        self.type=name
          
    def addNewValue(self, newValue):
        
        if self.startedTime==None:    
            self.maxValue=newValue
            self.minValue=newValue
            self.startedTime=str(datetime.now())[:19]

        self.currValue=newValue
        self.timeData=str(datetime.now())[:19]
        self.count=self.count+1
        self.totalValue=self.totalValue+newValue
        self.avgValue=round(float(self.totalValue/self.count),3)
        
        if newValue>self.maxValue:
            self.maxValue=newValue
        if newValue<self.minValue:
            self.minValue=newValue
    
            
    def __str__(self):
         
        sensorInfo= str(self.type+":\n"
                       +"\tStartedSince:"+self.startedTime+"\n"
                       +"\tTime: "+str(self.timeData)+"\n"
                       +"\tCurrent"+self.type+":"+str(self.currValue)+"\n"
                       +"\tAverage"+self.type+":"+str(self.avgValue)+"\n"
                       +"\tSampleNum: "+str(self.count)+"\n"
                       +"\tMin_"+self.type+": "+str(self.minValue)+"\n"
                       +"\tMax_"+self.type+": "+str(self.maxValue))
        
        return sensorInfo  
      
    
    def toDict(self):
        dictSensorData={
        
                       "type":self.type,
                       "startedTime":self.startedTime,
                       "timeData":self.timeData,
                       "currValue":self.currValue,
                       "avgValue":self.avgValue,
                       "minValue":self.minValue,
                       "maxValue":self.maxValue,
                       "count":self.count,
                       "totalValue":self.totalValue
                    }
        return dictSensorData
        
      
    def toJson(self):
        
        return json.dumps(self.toDict())
    
       
    def toXml(self):
        
        data=Json2xml.fromstring(self.toJson()).data
        dataConverter=Json2xml(data)
        
        
        return dataConverter.json2xml()    
        
  

        
        
        
        
'''
Created on Nov 30, 2018

@author: Howson
'''

from paho.mqtt.client import Client


class MqttClientConnector():
    '''
    this class is to configure the MQTT connection. Define the method of publish and subscribe.
    and define what to when the message is arrived or after publishing and subscribing successfully.
    :param host: the MQTT broker we want to use
    :param port: the default port is 1883
    '''
               
    _host=None
    _port=None
    _brokerAddr=None
    _mqttClient=None
    
    
    def __init__(self,host,port,on_connect,on_message,on_publish,on_subscribe):
                  
        self._brokerAddr=host+":"+str(port)
        self._host=host
        self._port=port
        
        self._mqttClient=Client()
        self._mqttClient.on_connect=on_connect
        self._mqttClient.on_message=on_message
        self._mqttClient.on_publish=on_publish
        self._mqttClient.on_subscribe=on_subscribe
   
          
          
    def connect(self):
            
        try:
                    
            print("Connecting to broker:"+self._brokerAddr+".....")
            self._mqttClient.connect(self._host,self._port, 60)
            self._mqttClient.loop_start()
                
        except Exception as e:
                
            print("Cloud not connect to broker "+self._brokerAddr+" "+str(e))
                
            
    def disconnect(self):
            
        self._mqttClient.disconnect()
        self._mqttClient.loop_stop()
        
        
    '''
    :param topic : the topic we want to publish
    :param message : the content we want to publish
    :param qos: the qos we want to use
    '''    
    def publishMessage(self,topic,message,qos):
        
        print("Publishing message:"+message+" to broker: "+self._brokerAddr+" Topic:"+topic)
        self._mqttClient.publish(topic,message,qos)
    
    
    '''
    :param topic : the topic we want to subscribe
    :param qos: the qos we want to use
    '''  
    def subscribeTopic(self,topic,qos):
        
        print("Subscribing to topic:"+topic+".....")
        self._mqttClient.subscribe(topic,qos)
        def on_connect(mqttc,obj,flags,rc):
            print("Successfully Connect !!  : "+str(rc))
    
    # when the message is arrived, the message will trigger the actuator     
    def on_message(mqttc, obj, msg):
        
        print(" ActuatorData arrived from topic:"+msg.topic + " QoS:" + str(msg.qos) + " Message:" + str(msg.payload.decode("utf-8")))
        sh=sense_hat.SenseHat()
        enableLED=True
        while enableLED:
           mes = str( msg.payload)
           sh.show_message(str(msg.payload),scroll_speed=0.05)
           enableLED=False  

    # it will run when publish message successfully 
    def on_publish(mqttc, obj, mid):
        print("Successfully published SensorData to topic     - mid: " + str(mid) )


    #it will run when subscribe message successfully
    def on_subscribe(mqttc, obj, mid, granted_qos):
        print("Successfully Subscribed to topic  " + str(mid) + " Granted_QoS:" + str(granted_qos))

   
    
    




    

         

              
          
      
          
          
        
          
          
          
          
          
          
          
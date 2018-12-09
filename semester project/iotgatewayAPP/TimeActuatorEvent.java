package iotgateway;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/*
 * this class is to handle the message received from the broker. 
 * receive the sensor data from ubidot and then send it to the device.
 * @param loaclhost: the broker we want to connect
 * @param username: the access key of the ubidot
 * @param pemFileName: the path of the certificate file
 * @param subTopic: the topic name to subscribe 
 * @param pubTopic: the topic name to publish the message 
 */
public class TimeActuatorEvent extends MqttClientConnector{

	
   private String sensormes;      
   private String actuatormes;//tempActuator
   private String currentData;
   


	public TimeActuatorEvent(String localhost,String userName, String pemFileName, String subTopic,String pubTopic) 
	{
		
		super(localhost,userName,pemFileName);
		
		this.sensormes=subTopic;
		this.actuatormes=pubTopic;
		this.connect();
		this.sub();
		
		
	    
	}
	
	
	// this will be used when connection is lost
	@Override
	public void connectionLost(Throwable cause) {
		super.connectionLost(cause);
		
	}
	
	//this will be used when publish message successfully
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		super.deliveryComplete(token);
		System.out.println("Successfully publish to topic "+actuatormes);
	}
	
	
	//this will be used when the message arrived from broker and then publish this message to device
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		super.messageArrived(topic, message);
		System.out.println("Message Arrived from Topic "+sensormes);
		//get the actuator data we want
		currentData = message.toString();
		currentData = "!!!!change the time of bus: " + currentData;
		MqttClientConnector m =new MqttClientConnector("tcp", "34.238.168.201",1883);
		m.connect();
		boolean a= m.publishMessage(actuatormes, 0, currentData.getBytes());
	    m.disconnect();
		if(!a) {System.out.println("Failed to handle gatewayEvent");}
	}
	
	
    
	
	
	/*Subscribe sensorData from local broker 
	 * @param qos: the qos we want to set
	 * @return  if subscribe successfully return true else return false
	 */
	public boolean sub() 
	{		
		return super.subscribeTopic(sensormes, 1);
	}
	
}


package ioTConnectedDevicesGateWay.labs.module06;

public class MqttSubClientTestApp {

	public static void main(String[] args) {
//create the instance 
		
		MqttClientConnector _mqttClient= new MqttClientConnector("tcp", "iot.eclipse.org", 1883);
		_mqttClient.connect();
//subscribe the Test topic and use the level 2 of qos
		_mqttClient.subscribeTopic("Test",2);
		
	}

}

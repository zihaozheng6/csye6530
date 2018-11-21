package ioTConnectedDevicesGateWay.labs.module06;

//import ioTConnectedDevicesGateWay.labs.common.*;

public class MqttPubClientTestApp {

	public static void main(String[] args) {	

		MqttClientConnector mqttPubConnector = new MqttClientConnector("tcp", "iot.eclipse.org", 1883);
		mqttPubConnector.connect();
		String mes1 = "test 1";
		String mes2 = "test 2";
		String mes3 = "test 3";
		mqttPubConnector.publishMessage("Test", 0, mes1.getBytes());
		mqttPubConnector.publishMessage("Test", 1, mes2.getBytes());
		mqttPubConnector.publishMessage("Test", 2, mes3.getBytes());


	}

}

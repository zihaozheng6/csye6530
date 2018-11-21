package ioTConnectedDevicesGateWay.labs.module08;
import ioTConnectedDevicesGateWay.labs.module06.MqttClientConnector;
public class TempSensorPublisherApp {
	public static void main(String[] args) {
		String path = "/Users/Howson/Downloads/IoT-ConnectedDevices-GateWay/data/ubidots_cert.pem";
		MqttClientConnector test = new MqttClientConnector("things.ubidots.com", "A1E-xpFrJY08vApdSFDjpUS9JKQ1JsmPAm",
				path);
		test.connectUbidots();
		// publish from the sensor
		String topicname = "/v1.6/devices/homeiotgateway/tempsensor";
       
		int mes = 25 ;
		String payload = String.valueOf(mes);
		test.publishMessage(topicname, 0, payload.getBytes());
		int mes1 = 24 ;
		String payload1 = String.valueOf(mes1);
		test.publishMessage(topicname, 0, payload1.getBytes());
		int mes2 =23 ;
		String payload2 = String.valueOf(mes2);
		test.publishMessage(topicname, 0, payload2.getBytes());
		int mes3 = 20 ;
		String payload3 = String.valueOf(mes3);
		test.publishMessage(topicname, 0, payload3.getBytes());
		int mes4 = 19 ;
		String payload4 = String.valueOf(mes4);
		test.publishMessage(topicname, 0, payload4.getBytes());
		

	}

}

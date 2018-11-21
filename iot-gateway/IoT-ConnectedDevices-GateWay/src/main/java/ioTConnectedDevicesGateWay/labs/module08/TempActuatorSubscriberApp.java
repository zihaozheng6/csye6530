package ioTConnectedDevicesGateWay.labs.module08;
import ioTConnectedDevicesGateWay.labs.module06.MqttClientConnector;
public class TempActuatorSubscriberApp {

	public static void main(String[] args) {

		String path = "/Users/Howson/Downloads/IoT-ConnectedDevices-GateWay/data/ubidots_cert.pem";
		MqttClientConnector test = new MqttClientConnector("things.ubidots.com", "A1E-xpFrJY08vApdSFDjpUS9JKQ1JsmPAm",
				path);

		test.connectUbidots();
		String topicname = "/v1.6/devices/homeiotgateway/tempactuator/lv";
		test.subscribeTopic(topicname, 2);

	}
}

package iotgateway;

/*
 * this class is to start all the method to handle all the events.
 */
public class GatewayApp 
{
	public GatewayApp() {
    	CarEvent  carEvent= new CarEvent("tcp", "34.238.168.201", 1883, "iot/sensor", "iot/actuator");
    	
    	HttpEvent httpEvent= new HttpEvent("tcp","34.238.168.201", 1883, "iot/sensor", "https://csye6225-fall2018-zhengz:8080/csye6225Webapp/iotSensordata");

    	TimeActuatorEvent timeEvent = new TimeActuatorEvent("things.ubidots.com", "A1E-xpFrJY08vApdSFDjpUS9JKQ1JsmPAm","/Users/Howson/Downloads/IoT-ConnectedDevices-GateWay/data/ubidots_cert.pem" ,"/v1.6/devices/homeiotgateway/tempactuator/lv", "iot/act");
    	
		
	}
    public static void main( String[] args )
    {
       GatewayApp start = new GatewayApp();
    	
    }
}

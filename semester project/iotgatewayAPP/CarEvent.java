package iotgateway;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

/*
 * this class is to receive message from broker and then send actuator message back to device.
 * the sensor data will be analyzed to create a new car's actuator data and send email to user.
 * @param protocol: the protocol we want to use when sending message 
 * @param host: the broker we use when using MQTT connection 
 * @param port: the port used in MQTT connection
 * @param subTopic: the topic name to subscribe 
 * @param pubTopic: the topic name to publish message
 */
public class CarEvent extends MqttClientConnector{

   public Smtp smtp ;
   private String _subTopic;      
   private String _pubTopic;
   private SensorData _SensorData;      //tempSensor
   private String currentActuatorData;    //tempActuator
   


	public CarEvent(String protocol,String localhost,  int port, String subTopic,String pubTopic) 
	{
		
		super(protocol,localhost,port);
		this._SensorData=new SensorData();
		this._subTopic=subTopic;
		this._pubTopic=pubTopic;
		this.connect();
		this.sub();
		
	}
	
	
	
	
	
	/*
	 * when message arrived this is to change the message into Json and then get average data. 
	 *Create actuator data according to average data and send email.
	 *
	*/
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		super.messageArrived(topic, message);
		System.out.println("Message Arrived from Topic "+_subTopic);
		
		JSONObject sensorjsonMessage=null;
		 
		   try
		   {
		   //turn message into json and get average data from it 
			sensorjsonMessage=new JSONObject(message.toString());
			_SensorData.fromJson(sensorjsonMessage);
			System.out.println("  Succcessfully updatad new SensorData");
		   }catch (Exception e) 
		   {
		    System.out.println(e.getMessage());
		    return ;
		   }		
		   System.out.println(" create car actuator data...");
	  
		if(_SensorData.getAvgValue()>22)        
		{
		   currentActuatorData="increase the number of bus++++";
		   
    //send email when data is too high
		   smtp.send1(_SensorData.toString());
		   System.out.println("The email has been sent successfully");
		}
		else if(_SensorData.getAvgValue()<21.5)   
		{
			currentActuatorData="decrease the number of bus----";
	//send email when data is too low
			smtp.send1(_SensorData.toString());
			System.out.println("The email has been sent successfully");
		}
		else
		{
			currentActuatorData="no";
		}	
		
		//publish actuator data to broker
		super.publishMessage(_pubTopic, 2,currentActuatorData.getBytes());
		return ;
		
	}
	
	// this will be used when connection is lost
			@Override
	public void connectionLost(Throwable cause) {
				super.connectionLost(cause);
				
			}
			// this will be used when publish successfully
			@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
				super.deliveryComplete(token);
			}
	//Subscribe sensorData from local broker and set qos
	public boolean sub() 
	{		
		return super.subscribeTopic(_subTopic, 2);
	}
	
}

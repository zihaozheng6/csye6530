package iotgateway;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/*
 * this class is to receive message from broker and then send message to web by HTTP.
 * @param protocol: the protocol we want to use when sending message 
 * @param host: the broker we use when using MQTT connection 
 * @param port: the port used in MQTT connection
 * @param subTopic: the topic name to subscribe 
 * @param httpPostServiceURL: the address to send the message
 */
public class HttpEvent extends MqttClientConnector{

	
   private String subscribeTopic;      
   
   private String  httpPostServiceURL;
 
   

	public HttpEvent(String protocol, String host, int port,String subTopic,String httpPostServiceURL) {
		super(protocol,host,port);
		this.subscribeTopic=subTopic;
		this.httpPostServiceURL=httpPostServiceURL;
		this.connect();
		this.sub();
	}
	
	
	
	// this will be used when message arrived. And then send message to web which will also add sensor to database.
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		super.messageArrived(topic, message);
		System.out.println("Message Arrived from Topic "+subscribeTopic);
		   
		   try
			{
							
				CloseableHttpClient client = HttpClientBuilder.create().build();
	            HttpPost request = new HttpPost(httpPostServiceURL);
	            request.setHeader("Content-Type", "application/json");
	            request.setEntity(new StringEntity(message.toString())); 
	            
	            System.out.println("Sending post request to "+httpPostServiceURL);
	       //send the message received from broker  
	            HttpResponse response = client.execute(request);
	      //to find out whether the transaction is successful
	            if(response.getStatusLine().getStatusCode()==201)
	            {
	            	System.out.println("uploaded sensorData to DataBase seccessfully");
	            	return ;
	            }
	            System.out.println("fail to send sensor data to web ");
	            return ;
	  
			}
			catch (Exception e) 
			{
		         System.out.println("fail to send sensor data to DataBase"+e.getMessage());		
		         return ;
			}
		  
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
	
	//set the qos we want to use when subscribe topic
	public boolean sub() 
	{		
		return super.subscribeTopic(subscribeTopic, 1);
	}
	
}

package iotgateway;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/*
 * this class is to complete the function that we need to use when using MQTT connection
 * 
 * 
 */

public class MqttClientConnector implements MqttCallback {

	private static final Logger _Logger = Logger.getLogger(MqttClientConnector.class.getName());

	private String _protocol;
	private String _host;
	private int _port;

	private String _clientID;
	private String _brokerAddr;
	private MqttClient _mqttClient;



	private String _userName;
	private String _pemFileName;
	private boolean _isSecureConn;
	
//here is to set the protocol we choose to use and set the host and port for the broker address
	public MqttClientConnector(String protocol, String host, int port) {
		super();

		_protocol = protocol;
		_host = host;
		_port = port;
		_clientID = MqttClient.generateClientId();
		_brokerAddr = _protocol + "://" + _host + ":" + _port;
		
//showing the information of clientID and broker address
		_Logger.info("Using client ID for broker conn: " + _clientID);
		_Logger.info("Using URL for broker connecting : " + _brokerAddr);

	}
	
// update the new constructor for the module8 to set the host and file name
	public MqttClientConnector(String host, String userName, String pemFileName) {
		super();
		if (host != null && host.trim().length() > 0) {
			_host = host;
		}
		if (userName != null && userName.trim().length() > 0) {
			_userName = userName;
		}
		if (pemFileName != null) {
			File file = new File(pemFileName);
			if (file.exists()) {
				_protocol = "ssl";
				_port = 8883;
				_pemFileName = pemFileName;
				_isSecureConn = true;
				_Logger.info("PEM file valid. Using secure connection: " + _pemFileName);
			} else {
				_Logger.warning("PEM file invalid. Using insecure connection: " + pemFileName);
			}
		}
		_clientID = MqttClient.generateClientId();
		_brokerAddr = _protocol + "://" + _host + ":" + _port;
		_Logger.info("Using URL for broker conn: " + _brokerAddr);
	}

// public method which is used to connect the mqtt broker	
	public void connect() {
		if (_mqttClient == null) {
// set the parameter of the mqtt client
			MemoryPersistence persistence = new MemoryPersistence();
			try {
				_mqttClient = new MqttClient(_brokerAddr, _clientID, persistence);
// connect to mqtt connect options
				MqttConnectOptions connOpets = new MqttConnectOptions();
				connOpets.setCleanSession(true);
				_mqttClient.setCallback(this);
				_mqttClient.connect(connOpets);
				_Logger.info("Connecting to broker: " + _brokerAddr);

			} catch (MqttException e) {
				_Logger.log(Level.SEVERE, "Failed to connect to broker: " + _brokerAddr, e);
			}
		}

	}

	
//update connect method to connect Ubidots
	public void connectUbidots() {
		if (_mqttClient == null) {
			MemoryPersistence persistence = new MemoryPersistence();
			try {
				_mqttClient = new MqttClient(_brokerAddr, _clientID, persistence);

				MqttConnectOptions connOpts = new MqttConnectOptions();
				
				connOpts.setCleanSession(true);
				if (_userName != null) {
					connOpts.setUserName(_userName);
				}
				// we need to complete those when using a secure connection, 
				if (_isSecureConn) {
					initSecureConnection(connOpts);
				}
				_mqttClient.setCallback(this);
				_mqttClient.connect(connOpts);
				_Logger.info("Connected to broker: " + _brokerAddr);
			} catch (MqttException e) {
				_Logger.log(Level.SEVERE, "Failed to connect to broker: " + _brokerAddr, e);
			}
		}
	}
	
	
	// to use secure MQTT connection, we need to Configure the TLS, 
		private void initSecureConnection(MqttConnectOptions connOpts) {
			try {
				_Logger.info("Configuring TLS...");
				SSLContext sslContext = SSLContext.getInstance("SSL");
				KeyStore keyStore = readCertificate();
				TrustManagerFactory trustManagerFactory = TrustManagerFactory
						.getInstance(TrustManagerFactory.getDefaultAlgorithm());

				trustManagerFactory.init(keyStore);
				sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

				connOpts.setSocketFactory(sslContext.getSocketFactory());
				_Logger.info("Configuring TLS done...");

			} catch (Exception e) {
				_Logger.log(Level.SEVERE, "Failed to initialize secure MQTT connection.", e);
			}
		}

	// to read information from the PEMfile
		private KeyStore readCertificate()
				throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream fis = new FileInputStream(_pemFileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			ks.load(null);
			while (bis.available() > 0) {
				Certificate cert = cf.generateCertificate(bis);
				ks.setCertificateEntry("jy_store" + bis.available(), cert);
			}
			return ks;
		}
	
//disconnect from the broker we have connected
	public void disconnect() {
		try {
			_mqttClient.disconnect();
			_Logger.info("Disconnected from broker: " + _brokerAddr);
		} catch (MqttException e) {
			_Logger.log(Level.SEVERE, "Failed to disconnect from broker: " + _brokerAddr, e);
		}

	}

//	this method is to send message to the topic of the broker
//  we can define the topic name and choose qos level
	public boolean publishMessage(String topic, int qos, byte[] payload) {
		boolean success = false;
		try {
			_Logger.info("Publishing message to topic: " + topic);
// set the payload of the message
			MqttMessage message = new MqttMessage();
			message.setPayload(payload);
// set  the qos level 
			message.setQos(qos);
// link to the topic
			_mqttClient.publish(topic, message);
			_Logger.info("Published Success!  MqttMessageID:" + message.getId());
			success = true;
		} catch (MqttException e) {
			_Logger.log(Level.SEVERE, "Failed to publish MQTT message: " + e.getMessage());
		}

		return success;

	}

//	this method is to subscribe all the topics
	public boolean subscribeToAll() {
		try {
			_mqttClient.subscribe("$SYS/#");
			return true;
		} catch (MqttException e) {
			_Logger.log(Level.WARNING, "Failed to subscribe to all topics.", e);
		}
		return false;
	}

//this method is to subscribe the special topic 
	public boolean subscribeTopic(String topic, int qos) {
		try {
			_mqttClient.subscribe(topic, qos);
			_Logger.info("Subscribed to Topic:  " + topic);
			return true;
		} catch (MqttException e) {
			_Logger.log(Level.WARNING, "Failed to subscribe  topics.", e);
		}
		return false;
	}

// this method is to show the status of the connection
	public void connectionLost(Throwable t) {
		_Logger.log(Level.WARNING, "Connection to broker lost. Will retry soon.", t);
	}

// this method is to publish the information when the delivery is completed
	public void deliveryComplete(IMqttDeliveryToken token) {
		try {
			_Logger.info("Delivery complete: ID = " + token.getMessageId() + " - " + token.getResponse() + " - "
					+ token.getMessage());
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Failed to retrieve message from token.", e);
		}

	}

// call back when message arrive 
	public void messageArrived(String data, MqttMessage msg) throws Exception {
		// TODO: should you analyze the message or just log it?
		_Logger.info("Message arrived: " + data + ", " + msg.getId() + ", PAYLOAD : " + new String(msg.getPayload()));
	}

}

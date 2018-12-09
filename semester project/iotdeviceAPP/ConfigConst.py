'''
Created on 2018年9月15日

@author: howson
'''
SECTION_SEPARATOR = '.'
DEFAULT_CONFIG_FILE_NAME = '../../../data/ConnectedDevicesConfig.props'

#Option
CLOUD              = 'cloud'
MQTT               = 'mqtt'
COAP               = 'coap'
SMTP               = 'smtp'
GATEWAY_DEVICE     = 'gateway'
CONSTRAINED_DEVICE = 'device'
UBIDOTS            = 'ubidots'

#Section name
UBIDOTS_CLOUD_SECTION = UBIDOTS + SECTION_SEPARATOR + CLOUD
SMTP_CLOUD_SECTION   = SMTP + SECTION_SEPARATOR + CLOUD
MQTT_CLOUD_SECTION   = MQTT + SECTION_SEPARATOR + CLOUD
COAP_CLOUD_SECTION   = COAP + SECTION_SEPARATOR + CLOUD
MQTT_GATEWAY_SECTION = MQTT + SECTION_SEPARATOR + GATEWAY_DEVICE
COAP_GATEWAY_SECTION = COAP + SECTION_SEPARATOR + GATEWAY_DEVICE
MQTT_DEVICE_SECTION  = MQTT + SECTION_SEPARATOR + CONSTRAINED_DEVICE
COAP_DEVICE_SECTION  = COAP + SECTION_SEPARATOR + CONSTRAINED_DEVICE

#Cloud
CLOUD_API_KEY     = 'apiKey'
CLOUD_MQTT_BROKER = 'mqttBroker'
CLOUD_MQTT_PORT   = 'mqttPort'
CLOUD_COAP_HOST   = 'coapHost'
CLOUD_COAP_PORT   = 'coapPort'

#Address related
FROM_ADDRESS_KEY     = 'fromAddr'
TO_ADDRESS_KEY       = 'toAddr'
TO_MEDIA_ADDRESS_KEY = 'toMediaAddr'
TO_TXT_ADDRESS_KEY   = 'toTxtAddr'

#Host and Port
HOST_KEY        = 'host'
PORT_KEY        = 'port'
SECURE_PORT_KEY = 'securePort'

#User
USER_NAME_TOKEN_KEY = 'userNameToken'
USER_AUTH_TOKEN_KEY = 'authToken'

#Enable
ENABLE_AUTH_KEY     = 'enableAuth'
ENABLE_CRYPT_KEY    = 'enableCrypt'
ENABLE_EMULATOR_KEY = 'enableEmulator'
ENABLE_LOGGING_KEY  = 'enableLogging'
POLL_CYCLES_KEY     = 'pollCycleSecs'

NOMINAL_TEMP_KEY    = 'nominalTemp'

# SMTP = 'smtp'
# MQTT = 'mqtt'
# 
# SECTION_SEPARATOR = '.'
# CLOUD = 'cloud'
# 
# 
# 
# 
# SMTP_CLOUD_SECTION = SMTP + SECTION_SEPARATOR + CLOUD
# MQTT_CLOUD_SECTION = MQTT + SECTION_SEPARATOR + CLOUD
# 
# HOST_KEY = 'smtp.gmail.com'
# PORT_KEY = '465'
# FROM_ADDRESS_KEY = 'jiaoyi199507@gmail.com'
# TO_ADDRESS_KEY = 'z597735908@gmail.com'
# USER_AUTH_TOKEN_KEY = 'jxjy2006'
#"jiaoyi199507@gmail.com","jxjy2006"


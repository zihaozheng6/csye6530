#coding:utf-8   #强制使用utf-8编码格式
'''
Created on 2018年9月15日

@author: howson
'''
      


import threading
import smtplib

from labs.common import ConfigUtil
from labs.common import ConfigConst

from email.mime.text import MIMEText 
from email.mime.multipart import MIMEMultipart
from labs.common import SensorData

Sens = SensorData.SensorData()


class SmtpClientConnector (threading.Thread):
    def __init__(self):
        self.config = ConfigUtil.ConfigUtil('../../../data/ConnectedDevicesConfig.props')
        self.config.loadConfig()
        #print('Configuration data...\n' + str(self.config)) 
        
        
    #HOST_KEY,PORT_KEY,FROM_ADDRESS_KEY,TO_ADDRESS_KEY,USER_AUTH_TOKEN_KEY
    def publishMessage(self, topic, data):
        host = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.HOST_KEY)
        port = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.PORT_KEY)
        fromAddr = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.FROM_ADDRESS_KEY)
        toAddr = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.TO_ADDRESS_KEY)
        authToken = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY)
        
        msg = MIMEMultipart()  #mimeMultipart
        msg['From'] = fromAddr
        msg['To'] = toAddr
        msg['Subject'] = topic
        msgBody = str(data)
        msg.attach(MIMEText(msgBody))   #mimeText  
        msgText = msg.as_string()
        # send e-mail notification
        """
        smtpServer = smtplib.SMTP_SSL(host, port)
        smtpServer.ehlo()
        smtpServer.login(fromAddr, authToken)
        smtpServer.sendmail(fromAddr, toAddr, msgText)
        smtpServer.close()
        """
   
        
        def mail():
            ret=True
            try:
                print ('The content of msgText:')
                print (str(msgText))
                server=smtplib.SMTP_SSL(host,port)  #发件人邮箱中的SMTP服务器，端口是25
                
                #server.ehlo()  # 向SMTP服务器打一个招呼，查看连接状态
                #server.starttls()  # 连接使用TLS加密(然后就可以传递敏感信息了)，否则无法使用login方法登陆会提示SMTPServerDisconnected 异常
                server.login(fromAddr,authToken)    #括号中对应的是发件人邮箱账号、邮箱密码
                
                
                server.sendmail(fromAddr,toAddr,msgText)    #括号中对应的是发件人邮箱账号、收件人邮箱账号、发送邮件
                
                server.quit()   #这句是关闭连接的意思
                server.close()
                
            except Exception:   #如果try中的语句没有执行，则会执行下面的ret=False
                ret=False
            return ret
        
        
        
        ret=mail()
        if ret:
            print ('\n ')
            print ("The Email was sent ") #如果发送成功则会返回ok，稍等20秒左右就可以收到邮件
            print('finish!!' )
            print('-------------------------------------------------')
        else:
            print ('\n ')
            print("Filed to send the email!")  #如果发送失败则会返回filed
            
        
# server=smtplib.SMTP_SSL("smtp.163.com",465)  #发件人邮箱中的SMTP服务器，端口是25
# print ('1')
#                 #server.ehlo()  # 向SMTP服务器打一个招呼，查看连接状态
#                 #server.starttls()  # 连接使用TLS加密(然后就可以传递敏感信息了)，否则无法使用login方法登陆会提示SMTPServerDisconnected 异常
# server.login("z6175159040@163.com","zzz666")    #括号中对应的是发件人邮箱账号、邮箱密码
# print('2')
#                 #server.sendmail(fromAddr,[toAddr,],msgText)
#                 
# server.sendmail("z6175159040@163.com","z597735908@gmail.com","msgText")
            
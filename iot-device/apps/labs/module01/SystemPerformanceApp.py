'''
Created on 2018年9月22日

@author: Howson
'''
from time           import sleep
from labs.module01  import SystemPerformanceAdaptor
sysPerfAdaptor = SystemPerformanceAdaptor.SystemPerformanceAdaptor()
sysPerfAdaptor.daemon = True
print("Starting system performance app daemon thread...")
#    sysPerfAdaptor.setEnableAdaptorFlag(True)
sysPerfAdaptor.start()
while (True):
        sleep(5)
        pass
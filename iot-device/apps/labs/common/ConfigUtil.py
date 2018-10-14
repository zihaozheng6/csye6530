'''
Created on 2018年9月15日

@author: howson
'''
import configparser
import os

DEFAULT_CONFIG_FILE = '../../../data/ConnectedDevicesConfig.props'
class ConfigUtil():
    configfile = DEFAULT_CONFIG_FILE
    configdata = configparser.ConfigParser()
    isLoaded   = False
    
    def __init__(self,configFile):
        if(configFile != ''):
            self.configfile = configFile
    
    def loadConfig(self):
        print(DEFAULT_CONFIG_FILE)
        if(os.path.exists(self.configfile)):
            self.configdata.read(self.configfile)
            self.isLoaded = True
    
    def getConfig(self, forceReload = False):
        if(self.isLoaded == False or forceReload):
            self.loadConfig()
        return self.configdata
    
    def getConfigFile(self):
        return self.configfile

    def getProperty(self,section, key, forceReload = False):
        return self.getConfig(forceReload).get(section, key)
    
    def isConfigDataLoaded(self):
        return self.isLoaded
    
    
    
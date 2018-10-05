'''
Created on 2018年9月29日

@author: Howson
'''

import configparser


config = configparser.ConfigParser()


class ConfigUtil():  
    def __init__(self,fileAddr):  
        self.fileAddr=fileAddr  
        print ('========== Environment Setting ==========')
        print ('Already get the fileAddr:' + str(fileAddr))
        # self.config={}  
        
    def loadConfig(self):  
        self.config = config.read(self.fileAddr)
        print ('Already get the config:' + str(self.config))

    def getProperty(self,section,key):  
        self.section = section
        #sect = config.sections()
        #print ('Section name:' + str(sect))
        #print ('Self.section name:' + str(self.section))
        self.key = key
        #print ('KEY value:' + str(self.key))

        return self.key 
'''
Created on Sep 29, 2018

@author: Howson
'''

class SenseHat:
    '''
    this class is to control the actuator of the pi.
    '''
    rotateDeg = 270
    clearFlag = False

    def __init__(self):
        self.set_rotation(self.rotateDeg)
        
    def clear(self):
        self.clearFlag = True
        
    def get_humidity(self):
        return 48.5

    def get_temperature(self):
        return self.get_temperature_from_humidity()

    def get_temperature_from_humidity(self):
# NOTE: This is just a sample
        return 31.5

    def get_temperature_from_pressure(self):
        return self.get_temperature_from_humidity()

    def get_pressure(self):
# NOTE: This is just a sample
        return 31.5
    def set_rotation(self, rotateDeg):
        self.rotateDeg = rotateDeg

    def show_letter(self, val):
        print(val)
    
    def show_message(self, msg,scroll_speed):
        print(msg)
        
    def set_pixel(self,x,y,color):
        return None
    
    def set_pixels(self,image):
        return None


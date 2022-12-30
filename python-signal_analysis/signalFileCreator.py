## import the serial library
import serial

## Boolean variable that will represent
## whether or not the arduino is connected
connected = False
isShooting = False
strValue = ''
value = 0
upcounter = 0
downcounter = 0
## establish connection to the serial port that your arduino
## is connected to.

locations=['COM1','COM2','COM3']

for device in locations:
    try:
        print("Trying...",device)
        ser = serial.Serial(device, 9600, timeout = .1)
        print("Connected to", device)
        break
    except:
        continue

## loop until the arduino tells us it is ready
while not connected:
    serin = ser.read()
    connected = True

## open text file to store the current


## read serial data from arduino and
## write it to the text file 'position.txt'
while 1:
    try:
        if ser.inWaiting():
            x = ser.read()
            x = x.decode('utf-8')
            if x != '\n' and x != '.':
                strValue = strValue + x
            elif x == '\n' and strValue != '':
                value = int(strValue)/100
                if value > 3.5:
                    if isShooting == False: 
                        upcounter += 1
                        if upcounter > 5:
                            print(value)
                            text_file = open("signal.txt", 'w')
                            text_file.close()
                            isShooting = True
                            
                            value = 0
                            upcounter = 0
                else:
                    downcounter += 1
                    if downcounter > 5:
                        isShooting = False
                        downcounter = 0
                strValue = ''
    except:
        continue

## close the serial connection and text file

print("Done")
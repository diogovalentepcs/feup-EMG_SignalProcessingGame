## import the serial library
import serial

## Boolean variable that will represent
## whether or not the arduino is connected
connected = False

strValue = ''
value = 0
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

text_file = open("fullsignal.txt", 'w')
## read serial data from arduino and
## write it to the text file 'position.txt'
while 1:
        if ser.inWaiting():
            x = ser.read()
            x = x.decode('utf-8')
            if x != '\n' and x != '.':
                strValue = strValue + x
            elif x == '\n' and strValue != '':
                value = int(strValue)/100
                print(value)
                text_file.write(str(value))
                text_file.write(x)
                strValue = ''

## close the serial connection and text file
text_file.close()
ser.close()

print("Done")
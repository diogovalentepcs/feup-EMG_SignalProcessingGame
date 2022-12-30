import time

import matplotlib
import psutil
import matplotlib.pyplot as plt
import serial

from biosppy.signals import emg

x, y = [], []
counter = 0
with open('fullsignal.txt', 'r') as reader:
    line = reader.readline()
    while (line != ''):
        s = line.split()
        x.append(abs(float(s[0])))
        counter += 1
        line = reader.readline()

signal = emg.emg(x,1000,True)[1]
print(signal)


print(onsets)
print("Done")
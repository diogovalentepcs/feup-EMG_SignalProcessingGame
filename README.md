# EMG Signal Processing Game

Biomedical Laboratory Project:  Analysis of EMG signals of the arm to control a "Space Shooter" game 

This the project for an optional class I took (from the 3rd year of Biomedical Engineering course), which goal was to help biomedical students apply their knowledge in an end of Bachelor's project.
I completed the class with the second highest grade (17/20), without previous experience in the field.

Project Team:

* Diogo Valente Polónia
* Isabela Miranda
* Maria Inês Bastos

## Project Description

It was possible to implement, in a simple and robust way, a digital control system by electromyography (EMG) signal, for physiotherapy, but that can be used in several more complex applications, namely in the areas of e-sports, military simulation or any other that involves repetitive and minute movements and that can therefore benefit from practicing these in a safe and simulated environment.

The analog processing was first simulated in Multisim, and then implemented in a breadboard connected to an Arduino Uno for simple collection and A/D conversion. The resulting digital signal was processed in Matlab, but due to the lack of fast communication between Matlab and Java, the theorized processing for Matlab was replaced by simpler Python code capable of communicating efficiently with the interface developed in Java. This interface allows the user to play a small arcade style game, controlling the movement of an avatar through the computer keyboard and the firing of projectiles through the hand lock.

Here is a demo of the interface (game):

![Demo Gif](https://i.imgur.com/6dznVhu.gif)

You can access the [Project Report here](https://drive.google.com/file/d/1JeikYEIbkkhNLNkXbW-iCSKxFumxWU74/view?usp=share_link) (PT) and the [Project Presentation here](https://drive.google.com/file/d/1euCGas-w38VMisZapHg5wgMIBBdtydO9/view?usp=share_link)


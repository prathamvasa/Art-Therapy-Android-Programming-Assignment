OVERVIEW:
I have created a simple app that allows users to draw pictures. The app also reminds users to draw a picture whenever they unlock their phone.
I have used the following components of Android Application Development in this project: canvas, custom views, sensors, broadcast receivers, services, and notifications.


ACTIVITIES:
Draw Activity:
The draw activity has the following features:
A custom view that allows users to draw arbitrary pixels

When the user shakes the phone:
The drawing is reset.
An eraser sound plays.  
The sound plays in a different thread from the UI thread. 
I have used an IntentService.

When the phone is unlocked, a broadcast receiver is triggered. 
The broadcast receiver displays a notification, asking the user to make a drawing. 
If the user taps the notification, it launches the Draw Activity.

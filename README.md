# UpdateFromThread demo

This demo shows how to do work on a thread and update the state of the app Swing GUI from that thread.

Classes:

* `DotModel` contains a coordinate point which is moved within a rectangle, bouncing from the "walls". Code is executed in a thread, changing the point every 40 ms.
* `DotModelObserver` is notified by the `DotModel` as the point coordinates change.
* `GraphicsPanel` draws the dot on on the coordinate point, and displays the coordinate in a `JLabel`.
* `GUIApp` implements the `main` and building the Swing GUI with `JFrame`, `GraphicsPanel` and the `DotModel`.

![Demo app screenshot](DemoappScreenshot.png)

Take a loot at the code in `DotModel.run()` to see what the thread does, and how it notifies the observer (`GraphicsPanel`) about the coordinate point change.

Then see how the `GraphicsPanel.dotMovedTo(int, int)` updates the coordinate point and tells Swing to update the view by calling `repaint()`. Basically, Swing components should not be directly manipulated here, e.g. calling drawing methods or updating label contents (things done in `paint()`).

Note what is printed out in the console when the demo is running. It shows the name of the thread in the context of which the `GraphicsPanel.dotMovedTo(int, int)` is called. 

Then comment out the two lines as instructed in the comments of `GraphicsPanel.dotMovedTo(int, int)`. Run the app again and now you see that the code is executed in the context of the `DotModel` worker thread, not the Swing thread as it is supposed to.

## Why this was done

This demo was done by Antti Juustila, INTERACT Research Unit, University of Oulu, Finland.

The demo is for an introductory course on GUI programming, where most of the students work with Java Swing.

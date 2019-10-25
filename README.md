# cdcbConsolidator
---
A GUI tool to consolidate CDCB files. 

## Current version

Requirements: You must have java 1.8+ installed on your computer! You can find the package to install the java 8 development kit [here](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).

#### Draft state one for early deployment (v 0.0.1)

The current state of the utility is the presence of one of three tabs for converting the flat files from the CDCB. This tab transposes the data from the ANIM and EVAL flat files into a horizontal format (each row == one animal) and uses a comma (",") delimiter in the output file. 

#### Future feature wishlist

I am planning to add two more tabs, with one tab allowing for simple, guided SQL searching of data present in these files and the other allowing for raw SQL input for advanced users. 

## Usage

Simply double-click the "jar" file to bring up the program window:

![GUI default look](https://github.com/njdbickhart/cdcbConsolidator/images/DefaultGui.PNG)

Right now, the program only uses the "Anim File" and "Eval File" fields for data conversion (that's basically all you need to emulate the old format!). To populate these fields, just click the "browse" button to the right of them to bring up a filed chooser:

![File Chooser Window](https://github.com/njdbickhart/cdcbConsolidator/images/file_chooser.PNG)

The file chooser window selects ".csv" files by default, but you can display all files by changing the "Files of type" dropdown menu at the bottom of the window.

In order to run the conversion, you will also need to define a new "Output" file name in the bottom text field. You may use the Browse button to select the location and name the new file. If you enter the text in the text field directly, the file may end up in your "Documents" folder by accident, so it is highly advised that you select the location with the "Browse" button!

![Options entered and ready to go](https://github.com/njdbickhart/cdcbConsolidator/images/Ready_to_go.PNG)

When you have all three text fields updated as listed in the image above, you are ready for the conversion! Just hit the "Convert" button and let the program do its magic. You can tell that the program has converted the files successfully if the progress bar turns orange as shown:

![Progress finished](https://github.com/njdbickhart/cdcbConsolidator/images/Done.PNG)

To clear the text fields so that you can enter a new file, just hit the "Clear" button. 

# Learning Objectives

This example is intended as a starting point for anyone planning develop
Android applications using Scala. Its learning objectives are:

- Android application development using Scala
    - using the Simple Build Tool (sbt) for Scala in conjunction with 
      [pfn's well-maintained plugin](https://github.com/pfn/android-sdk-plugin)
    - using IntelliJ IDEA
- Basic Android features 
    - [event-driven program execution](http://en.wikipedia.org/wiki/Event-driven_programming)
    - handling input events
    - distinguishing between regular and long click/touch events
    - showing a notification message (toast)
    - playing a notification sound
    - Use of Scala primarily as a "better Java" (including mutable state)

# Prerequisites

## Required

- Java Development Kit (JDK) 6 through your package management system or from
  [Oracle](http://www.oracle.com/technetwork/java/javase/downloads).
- [Android SDK](http://developer.android.com/sdk)
- [sbt](http://www.scala-sbt.org/)
- [android-sdk-plugin](https://github.com/pfn/android-sdk-plugin) for sbt
  (detailed instructions are half way down past the change log)

## Recommended

- [JetBrains IntelliJ IDEA 13.1 CE](http://www.jetbrains.com/idea)
- IDEA Scala plugin installed through the plugin manager

# Preparation

- Configure virtual machine hardware acceleration per
  [these instructions](http://developer.android.com/tools/devices/emulator.html#accel-vm).
- Create an Android Virtual Device (AVD) per
  [these instructions](http://developer.android.com/tools/devices). This
  device should support API level 19 (Android 4.4.2) and have an x86
  CPU, a skin with hardware controls, and the option _hardware
  keyboard present_ checked.
- If you have an Android device and wish to use it for development,
   you can follow
   [these instructions](http://developer.android.com/tools/device.html)
   to enable it.
- Check out this project using Mercurial (hg) or download
  [this zip file](https://bitbucket.org/loyolachicagocs_plsystems/clickcounter-android-rxscala/get/default.zip).

# Developing on the Command-line

These instructions assume that `$ANDROID_HOME/tools` and 
`$ANDROID_HOME/platform-tools` are in the `$PATH`.      

## Specifying the location of the Android SDK

You can either

- set `$ANDROID_HOME` to the directory where you installed your 
  Android SDK

- create a file `local.properties` in your project root 
  (or copy an existing one) with a single line
  
        sdk.dir=/location/of/android/sdk

## Starting the emulator

To start the emulator:

    $ emulator -avd YourAVD &

It will take the emulator a couple of minutes to boot to your AVD's home or
lock screen. If you set up hardware acceleration correctly, you will see

    HAX is working and emulator runs in fast virt mode

To verify that you have a connection with the emulator:

    $ adb devices

The resulting list should look like this:

    List of devices attached
    emulator-5554   device

If this is not the case, restart the adb server

    $ adb kill-server
    $ adb start-server

and check again.

## Viewing the log

In Android, all log messages typically carry a tag. 
In this example, the tag for the main activity is  

    private def TAG = "hello-android-activity"

You can then write tagged log messages like this:       
    
    Log.i(TAG, "onCreate")
    
You can view the complete log using this command:
    
    $ adb logcat
    
This quickly results in too much information. 
To view only the messages pertaining, say, to a particular tag, 
you can filter by that tag:
     
    $ adb logcat | grep hello

## Running the application

Once your emulator is running or device connected, you can run the app:

    $ sbt android:run

The app should now start in the emulator and you should be able to interact
with it.

## Running the tests

This command runs the unit tests and the Robolectric-based out-of-container
functional tests.

    $ sbt test
    
*In-container Android instrumentation tests are included (sharing a testcase 
superclass with the Robolectric tests) and work in principle, but not with
the current build file for reasons we do not yet understand.* 

# Developing with IntelliJ IDEA

## Generating the configuration files

This step requires that you have the `sbt-idea` plugin installed per the
instructions for pfn's plugin.

    $ sbt gen-idea
    
You will have to repeat this step after every change to the `build.sbt` file 
(see also under "adding dependencies" below.

## Opening the project in IDEA

Open *(not import)* the project through the initial dialog or `File > Open`.
You should now be able to edit the project with proper syntax-directed
editing and code completion.

Right after opening the project, you may be asked to confirm the location of
the Android manifest file.

## Running the tests and the application

Some aspects of generated IDEA Android/Scala project do not work out of the box.
We have found it easier to open a terminal within IDEA using 
`View > Tool Windows > Terminal` and running `sbt test` or `sbt android:run`
as desired. In the latter case, the app should start in the emulator and 
you should be able to interact with it.

# Adding dependencies

To add a dependency, you can usually

- look it up by name in the [Central Repository](http://search.maven.org) 
  or [MVNrepository](http://mvnrepository.com/)
- find the desired version (usually the latest released or stable version)
- select the sbt tab
- copy the portion _after_ `libraryDependencies +=`
- paste it into this section of `build.sbt` (followed by a comma)

        libraryDependencies ++= Seq(

If you are using IntelliJ IDEA, you will also need to

- rerun

        $ sbt gen-idea

- back in IDEA, confirm that you want to reload the project
 
- reconfirm the location of the Android manifest file

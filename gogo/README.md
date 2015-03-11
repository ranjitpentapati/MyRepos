gogo-isp
=

This project allows us to mock out gogo backend with some simple APIs for an all in one web application for the on-board portal. 

The design here is grow your own framework - its similar to DropWizard but far simpler and totally under our control. 

This could be starting point for new design - lets see how it goes.

IDE
=
The code has been tested on Eclipse. Import the code as a gradle project and it will correctly create multiple projects for you.

How to run
=

You need git and gradle to run this code. For mac users install homebrew and then install these two application before proceeding.

This code can be run directly via gradle (currently this is broken!!) or via tomcat on eclipse using wtp.


add instructions for git clone and how to run.

FEATURES:
=
* Wired with Netflix Hystrix

See https://github.com/Netflix/Hystrix/wiki/Dashboard for details. From this page find instructions to pull and run dashboard from command line and use http://localhost:8080/hystrix.stream
 
* Wired with Netflix Servo and Yammer Metrics for comparision. (yammer seems better IMHO).

Open jvisualvm and make sure you install the plugins for jmbeans. Open the tab and you will find all of the instrumented APIS. 

TODO:
=

* Clean up and document the code
* Correct gradle usage - multi-project with integration tests via TestNG?
* Add unit tests - start with manually created jax-rs code.
* Add metrics and compare to servo - code there yammer looking good.
* Add configuration via Netflix archaius
* Add support for cookie and put back to DynamoDb.
* Add support for graphite.
* Add findbugs.


Big Picture
=

* Could this be leveraged for evaluation between DropWizard, Spring Boot and Karylon? 
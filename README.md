# PIDIR - A raspberry PI directory service

Tired of having to go hunt down your R-Pis ip addresses? Maybe you'd like to be able to keep track of all your Pis status? Maybe you'd like to make your Pis discoverable by other Pis/Devices on your network?

Well this will do it for you! It's pretty much Eureka for Raspberry Pis.

## Current State
It's up and running! 

Here's what it can do at the moment:
* Directory Service can be deployed wehre ever you can run a Java App, I like Heroku for its simplicity, price(free) and git Actions which automatically deploy the main branch when it gets updated.
* Directory Service statuses Pis based on the time thats passed since it last heard from them. GREEN - active, YELLOW - MIA,  RED - Assumed dead
* R-Pi scripts are ready to be deployed on your devices and there is a nice [tutorial](https://github.com/leeclarke/pidir/tree/main/python) on how to set that up on crontab so it will report it's status every 5 minutes or whatever you want.

## TODOs
    * Add a function to the Python script to look up a Pi by name so that it can provided directory services to other known devices.
    * Make the Pi Status times configurable. Currently they are GREEEN: `<=6 mins`, YELLOW: `<=16 mins`, RED : `>16 minutes`
    * Build a UI
    * Add configurable notifications like alerting if a device goes YELLOW or RED
    * consider persistance of data and why that ight be useful. (Still debating it)
    * Do something with tags.


## PiDir Server setup

TODO
* Deploy on Heroku
* Run Local
* Run on a Pi (why not?)




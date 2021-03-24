# Pidir Raspberry Pi Setup

# Copy files

1. First up you need to move the files over to your Pi.
    - Ssh into you pi
    - create a folder for pidir  >`mkdir pidir` ,
    - go to folder >`cd pidir`
    - use curl to pull the files you need
        -  curl -LOJ https://raw.githubusercontent.com/leeclarke/pidir/main/python/pidir-status.py
        -  curl -LOJ https://raw.githubusercontent.com/leeclarke/pidir/main/python/pidir.config

2. Make pidir-status.py executable
    - `chmod u+x pidir-status.py`

3. Update the config to add your info.  I am deploying my pidir server on Heroku which is free for hobby accounts. Check out the Pidir server setup  [Readme.md]() for more info.
    - `nano pdir.config`
    - The following are the setting in the config:
        * **pidir-hostname=** Base url to your Pidir server. examples: localhost:8080, your-conainer-name.herokuapp.com
        * **pidir-host-port=** optional, if blank then is assumes port 80, you can leave this blank for heroku, you might want to set is i your running your server inside you network.
        * **name=** The Name you want to display for a specific Pi - I name my Pi's on location and or function, use any naming pattern you like but __names must be unique__ or you'll have a confusing mess on your hands. :)
        * **network=** This allows you to name multiple networks so if you have devices on separate LANs etc then they can have a unique name. I use "home" for my home LAN and I don't have another network at the moment. You can put any name you want here!

6. Install supporting packages for python, I use pip but feel free to use what you like i you have a preference!
    - `sudo apt install python-pip`
    - `sudo python -m pip install requests`

5. Run the script manually to ensure it's set up and works!
    - `./pidir-status.py`
        if everything went well you'll see output similar to the following:
        ```
        status: 200
        [{u'ip': u'192.168.2.5', u'tag': u'none', u'name': u'desktop-controller', u'timestamp': u'2021-03-24T14:32:30.312432', u'network': u'home'}]

        ```

6.  Schedule the script to run every 5 minutes using crontab
    - edit crontab  `crontab -e`
    - go to the very bottom of the file and add the following line
        `*/5 * * * * /home/pi/pidir/pidir-status.py`

    Note: if things don't seem to work out or if this is your first time setting up the script, you might want to log all the activity from the task for a little while just to catch any issues, if so add to the cron tab command like this `*/5 * * * * /home/pi/pidir/pidir-status.py > /home/pi/pidir/pidir.log 2>&1`

    That will write all errors etc to a log file. I would remove the logging after things are working or only log errors just to prevent a huge log file from being generated.

7. Wait for the status script to run and then hit your pidir API to see i things worked out.
[GET http://your-conainer-name.herokuapp.com/pis](http://your-conainer-name.herokuapp.com/pis)

    - Debugging things: If nothing happens you might want to take a look at your crontab logs
        `tail -fn 500 /var/log/cron.log`
    - to enable crontab logging see this [helpful post](https://raspberrypi.stackexchange.com/questions/3741/where-do-cron-error-message-go)

#!/usr/bin/python
#--------------------------------------
# Pidir - updateStatus :
#   Makes a POST request to the PiDir server specified in the config file.
#
#   Requires the creation of a configuration file in the same directory named pidir.config
#  Note: install requests locally  run.  >sudo python -m pip install requests
#--------------------------------------
import requests
import socket

def deriveIp():
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.connect(("8.8.8.8", 80))
    ipAddr = s.getsockname()[0]
    s.close()
    return ipAddr

def loadConfig():
    my_path = os.path.abspath(os.path.dirname(__file__))
    path = os.path.join(my_path, "pidir.config")
    split_properties=[line.split("=") for line in open(path)]
    return {key: value for key,value in split_properties }

props = loadConfig()
# print ""
# print "props >>"
# print  props

name = props.get("name").replace('\n', '')
ip = deriveIp()
net = props.get("network", "default").replace('\n', '')
tag = props.get("tag", "none").replace('\n', '')

r = requests.post("http://" + props.get("pidir-hostname", "localhost").replace('\n', '') +":"+ props.get("pidir-host-port","80").replace('\n', '') +"/pi/", json={"name":name, "ip":ip, "network":net, "tag":tag})
print "status:" , r.status_code
print r.json()

#!/bin/bash

set -e

chkconfig httpd off
chkconfig httpd24-httpd on
echo "service httpd stop" >> /etc/rc.local
echo "service httpd24-httpd start" >> /etc/rc.local

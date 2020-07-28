#!/bin/sh

# Create a folder under which tailing positions are to be stored, one per tailed source.
# Adjust permissions afterwards.
mkdir -p /var/log/td-agent/tmp
chown -R td-agent:td-agent /var/log/td-agent/tmp
chown -R td-agent:td-agent /var/run/td-agent

# All Ruby Libraries should be owned by td-agent.
chown -R td-agent:td-agent /opt/td-agent

# Move away the original conf file shipped with the RPM.
mv /etc/td-agent/td-agent.conf /etc/td-agent/td-agent.conf.default
echo "Backed-up original td-agent configuration"

# Replace it with what we prefer.
cat /etc/bake-scripts/td-agent.conf >> /etc/td-agent/td-agent.conf
echo "Written custom td-agent configuration"

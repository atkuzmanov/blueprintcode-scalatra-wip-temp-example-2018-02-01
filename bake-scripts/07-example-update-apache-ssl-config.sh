#!/bin/bash

cp /etc/httpd/conf.d/blueprintcode_ssl_termination.conf /opt/rh/httpd24/root/etc/httpd/conf.d/blueprintcode_ssl_termination.conf
cp /etc/httpd/conf.d/blueprintcode_http_termination.conf /opt/rh/httpd24/root/etc/httpd/conf.d/blueprintcode_http_termination.conf
patch /opt/rh/httpd24/root/etc/httpd/conf.d/blueprintcode_ssl_termination.conf /etc/bake-scripts/blueprintcode/ssl_termination.conf.diff
patch /opt/rh/httpd24/root/etc/httpd/conf.modules.d/00-mpm.conf /etc/bake-scripts/blueprintcode/mpm.conf.diff
patch /opt/rh/httpd24/root/etc/httpd/conf/httpd.conf /etc/bake-scripts/blueprintcode/http.conf.diff

# Enable mod_status module for collectd.
cat <<\EOF >> /opt/rh/httpd24/root/etc/httpd/conf/httpd.conf
ExtendedStatus on
<Location /mod_status>
  SetHandler server-status
</Location>
EOF

mv /etc/crlrestart.d/httpd /etc/crlrestart.d/httpd24-httpd
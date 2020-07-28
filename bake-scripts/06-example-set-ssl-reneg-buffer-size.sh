#!/bin/bash

echo "<Location />
  SSLRenegBufferSize 10485760
</Location>" > /opt/rh/httpd24/root/etc/httpd/conf.d/ssl-reneg-buffer.conf



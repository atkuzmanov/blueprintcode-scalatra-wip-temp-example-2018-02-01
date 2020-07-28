#!/bin/sh

echo ">>> increasing the ulimit to 65536 <<<"

cat > /etc/security/limits.conf <<EOF
td-agent hard  nofile  65536
td-agent soft  nofile  65536
root                hard  nofile  65536
root                soft  nofile  65536
EOF


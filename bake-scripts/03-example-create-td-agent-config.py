#!/usr/bin/env python

import sys
import json

# Setup the TD-AGENT CONFIG
TD_AGENT_CONFIG_FILE = "/etc/bake-scripts/td-agent.conf"
TD_AGENT_CONFIG = """
<source>
  type tail
  path /var/log/blueprintcode/output.log
  refresh_interval 30
  read_from_head true
  pos_file /var/log/td-agent/tmp/blueprintcode_output_log.pos
  tag blueprintcode_output_log
  format none
</source>

<source>
  type tail
  path /var/log/httpd24/access_log
  refresh_interval 30
  read_from_head true
  pos_file /var/log/td-agent/tmp/access_log.pos
  tag http_access_log
  format none
</source>

<source>
  type tail
  path /var/log/httpd24/error_log
  refresh_interval 30
  read_from_head true
  pos_file /var/log/td-agent/tmp/error_log.pos
  tag http_error_log
  format none
</source>

<source>
  type monitor_agent
  bind 127.0.0.1
  port 24220
</source>

<match http_access_log>
  log_level error
  type s3
  s3_bucket {0}
  path blueprintcode/{1}/http_access_log
  s3_region eu-west-1
  check_apikey_on_start true
  buffer_type memory
  time_slice_format %Y%m%d%H
  flush_interval 60s
  buffer_chunk_limit 64m
  format single_value
  store_as text
  num_threads 1
</match>

<match http_error_log>
  log_level error
  type s3
  s3_bucket {0}
  path blueprintcode/{1}/http_error_log
  s3_region eu-west-1
  check_apikey_on_start true
  buffer_type memory
  time_slice_format %Y%m%d%H
  flush_interval 60s
  buffer_chunk_limit 64m
  format single_value
  store_as text
  num_threads 1
</match>

<match blueprintcode_output_log>
  log_level error
  type s3
  s3_bucket {0}
  path blueprintcode/{1}/application_logs
  s3_region eu-west-1
  check_apikey_on_start true
  buffer_type memory
  time_slice_format %Y%m%d%H
  flush_interval 60s
  buffer_chunk_limit 64m
  format single_value
  store_as text
  num_threads 1
</match>
"""

def main(component_json_path):
    config = json.load(open(component_json_path))

    try:
        bucket = config["configuration"]["s3_bucket_name"]
        environment = config["environment"]
        print "bucket name: ", bucket
        print "environment: ", environment
    except KeyError:
        raise Exception("key 'configuration/s3_bucket_name' and / or 'environment' are missing from config")

    with open(TD_AGENT_CONFIG_FILE, "a") as f:
        f.write(TD_AGENT_CONFIG.format(bucket, environment))

print "sys.arg", sys.argv[1]

if __name__ == "__main__":
    main(sys.argv[1])

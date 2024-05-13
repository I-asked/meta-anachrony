#!/bin/sh
set -Eeuo pipefail
mkdir -p ${HOME}/.b2g
cat <<EOF >${HOME}/.b2g/api-daemon.toml
[general]
host = "127.0.0.1"
port = 80
message_max_time = 10 # In ms.
verbose_log = false
log_path = "/tmp"
socket_path = "/tmp/api-daemon-socket"

[http]
root_path = "/usr/share/api-daemon/http_root"

[vhost]
root_path = "${HOME}/.b2g/webapps/vroot"
csp = "default-src * data: blob:; script-src 'self' http://127.0.0.1 http://shared.localhost; object-src 'none'; style-src 'self' 'unsafe-inline' http://shared.localhost"

[apps_service]
root_path = "/opt/b2g/webapps"
data_path = "${HOME}/.b2g/webapps"
uds_path = "/tmp/apps_service_uds.sock"
cert_type = "test"
updater_socket = "/tmp/updater_socket"
allow_remove_preloaded = true

[procmanager_service]
socket_path = "/tmp/b2gkiller_hints"
hints_path = "/tmp/prochints.dat"
EOF

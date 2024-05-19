#!/bin/sh
set -Eeuo pipefail
mkdir -p ${HOME}/.b2g/profile
cat <<EOF >${HOME}/.b2g/api-daemon.toml
[general]
host = "127.0.0.1"
port = 80
message_max_time = 10 # In ms.
verbose_log = false
log_path = "/tmp"
socket_path = "/tmp/api-daemon-socket"
remote_services_config = "${HOME}/.b2g/api-daemon/remote/config.toml"
remote_services_path = "${HOME}/.b2g/api-daemon/remote"

[http]
root_path = "/usr/share/api-daemon/http_root"

[vhost]
root_path = "${HOME}/.b2g/profile/webapps/vroot"
csp = "default-src * data: blob:; script-src 'self' http://127.0.0.1 http://shared.localhost; object-src 'none'; style-src 'self' 'unsafe-inline' http://shared.localhost"
report_csp = true

[apps_service]
root_path = "/opt/b2g/webapps"
data_path = "${HOME}/.b2g/profile/webapps"
uds_path = "/tmp/apps_service_uds.sock"
cert_type = "production"
updater_socket = "/dev/socket/updater_socket"
user_agent = "Mozilla/5.0 (Mobile; rv:95.0) Gecko/95.0 Firefox/95.0 B2GOS/3.0"
allow_remove_preloaded = true

[procmanager_service]
socket_path = "/tmp/b2gkiller_hints"
hints_path = "/tmp/prochints.dat"

[content_manager]
storage_path = "${HOME}/.b2g/costaeres"
metadata_cache_capacity = 250

[dweb]
storage_path = "${HOME}/.b2g/dweb"
EOF
test -e ${HOME}/.b2g/profile/user.js || cat <<EOF >${HOME}/.b2g/profile/user.js
//
// Preferences for B2G
//

user_pref("b2g.system_startup_url", "chrome://system/content/index.html");

user_pref(
  "b2g.neterror.url",
  "http://system.localhost/net_error.html"
);

// Enable tracking protection
user_pref("privacy.trackingprotection.enabled", true);
user_pref("privacy.trackingprotection.pbmode.enabled", true);

// Enable the backdrop-filter CSS property support.
user_pref("layout.css.backdrop-filter.enabled", true);

user_pref("browser.dom.window.dump.enabled", true);
user_pref("devtools.console.stdout.chrome", true);

user_pref("domsecurity.skip_html_fragment_assertion", true);

user_pref("dom.virtualcursor.enabled", false);

user_pref("consoleservice.logcat", false);

user_pref("device.sensors.enabled", true);
user_pref("device.sensors.motion.enabled", true);
user_pref("device.sensors.orientation.enabled", true);
user_pref("device.sensors.proximity.enabled", true);
user_pref("device.sensors.ambientLight.enabled", true);

// APZ physics settings (fling acceleration, fling curving and axis lock) have
// been reviewed by UX
user_pref("apz.axis_lock.breakout_angle", "0.7853982"); // PI / 4 (45 degrees)
user_pref("apz.axis_lock.mode", 2); // Use "strict" axis locking
user_pref("apz.content_response_timeout", 600);
user_pref("apz.drag.enabled", false);
user_pref("apz.fling_accel_interval_ms", 750);
user_pref("apz.fling_curve_function_x1", "0.59");
user_pref("apz.fling_curve_function_y1", "0.46");
user_pref("apz.fling_curve_function_x2", "0.05");
user_pref("apz.fling_curve_function_y2", "1.00");
user_pref("apz.fling_curve_threshold_inches_per_ms", "0.01");
// apz.fling_friction and apz.fling_stopped_threshold are currently ignored by Fennec.
user_pref("apz.fling_friction", "0.004");
user_pref("apz.fling_stopped_threshold", "0.0");
user_pref("apz.max_velocity_inches_per_ms", "0.07");
user_pref("apz.overscroll.enabled", false);
user_pref("apz.second_tap_tolerance", "0.3");
user_pref("apz.touch_move_tolerance", "0.03");
user_pref("apz.touch_start_tolerance", "0.06");

user_pref("security.sandbox.content.level", 3);

user_pref("browser.contentblocking.category", "strict");
user_pref(
  "browser.contentblocking.features.strict",
  "tp,tpPrivate,cookieBehavior5,cookieBehaviorPBM5,cm,fp,stp,lvl2"
);
user_pref("privacy.trackingprotection.lower_network_priority", true);
user_pref("privacy.trackingprotection.enabled", true);

user_pref("browser.tabs.remote.autostart", true);
user_pref("extensions.webextensions.remote", true);
user_pref("extensions.webextensions.background-delayed-startup", true);

// For the ImageCapture() api to take photos.
user_pref("dom.imagecapture.enabled", true);

// Don't complain about missing nm
user_pref("network.gonk.manage-offline-status", false);
user_pref("b2g.wifi.nmcli-path", "/bin/true");

user_pref("apz.overscroll.enabled", true);

user_pref("dom.dialog_element.enabled", true);

user_pref("datareporting.healthreport.service.enabled", false);
user_pref("datareporting.healthreport.uploadEnabled", false);

// Allow remote debugging without a ssh tunnel.
user_pref("devtools.debugger.force-local", false);

// Disable use of XDG portal for settings / look-and-feel information
user_pref("widget.use-xdg-desktop-portal.settings", 0);

// Force use of WebRender
user_pref("gfx.webrender.enabled", true);
user_pref("gfx.webrender.all", true);
user_pref("gfx.webrender.compositor", true);

// Enable pipewire camera
user_pref("media.webrtc.camera.allow-pipewire", true);

// Enable WebGL everywhere
user_pref("webgl.force-enabled", true);
EOF
mkdir -p "${HOME}/.b2g/profile/webapps/"
ln -sf "/opt/b2g/webapps/system" "${HOME}/.b2g/profile/webapps/"

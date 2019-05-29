# set the following variable to your one and only application which should
# be started

INITIAL_APP_PKGS ?= "qtsmarthome"
INITIAL_PATH ?= "/usr/share/qtsmarthome-1.0"
X_APPLICATION ?= "${INITIAL_PATH}/smarthome"

FILESEXTRAPATHS_prepend := "${THISDIR}/x-window-simple-app:"
require recipes-graphics/x-window-simple-app/x-window-simple-app.inc

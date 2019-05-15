# set the following variable to your one and only application which should
# be started

RDEPENDS ?= "xterm"
INITIAL_PATH ?= ""
X_APPLICATION ?= "/usr/bin/xterm"

FILESEXTRAPATHS_prepend := "${THISDIR}/x-window-simple-app:"
require recipes-graphics/x-window-simple-app/x-window-simple-app.inc

SECTION = "x11/libs"
SUMMARY = "x11 application autostart"
DESCRIPTION = \
"This installs a /usr/bin/x-window-manager script. The script will start the \
one and only application X_APPLICATION as the last step of the \
xserver-nodm-init X initialization. \
If the script ever returns, X will be killed."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit allarch

S = "${WORKDIR}"

INITIAL_PATH ?= "/usr/share/qtsmarthome-1.0"
X_APPLICATION ?= "${INITIAL_PATH}/smarthome"

SRC_URI = " \
    file://x-window-manager.in \
    file://set_have_touch.sh \
"

do_compile () {
    sed -e "s:@PATH@:${INITIAL_PATH}:" -e "s:@APP@:${X_APPLICATION}:" x-window-manager.in > x-window-manager
}

do_install () {
    install -d ${D}/${bindir} ${D}${sysconfdir}/X11/Xsession.d
    install -m 0755 ${S}/x-window-manager ${D}/${bindir}
    install -m 0644 ${S}/set_have_touch.sh ${D}/${sysconfdir}/X11/Xsession.d/29set_have_touch.sh
}

# make sure xinput_calibrator is only started once
pkg_postinst_${PN}() {
    rm /etc/xdg/autostart/xinput_calibrator.desktop
}

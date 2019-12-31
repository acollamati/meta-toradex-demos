SUMMARY = "Wayland application autostart"
DESCRIPTION = "This will start a wayland application after the wayland socket has been created."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit allarch systemd

RDEPENDS_${PN} += "weston-init ${INITIAL_APP_PKGS}"

S = "${WORKDIR}"

SRC_URI = " \
    file://wayland-app-launch.service \
    file://wayland-app-launch.sh.in \
"
FILESEXTRAPATHS_prepend := "${THISDIR}/wayland-app-launch:"

do_compile () {
    sed -e "s:@@wayland-application@@:${WAYLAND_APPLICATION}:" wayland-app-launch.sh.in > wayland-app-launch.sh
}

do_install () {
    install -d ${D}/${bindir} ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/wayland-app-launch.service ${D}${systemd_unitdir}/system
    install -m 0755 ${S}/wayland-app-launch.sh ${D}/${bindir}
}

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "wayland-app-launch.service"
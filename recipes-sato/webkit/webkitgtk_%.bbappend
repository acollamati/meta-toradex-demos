FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "file://minibrowser.desktop"

do_install_append() {
    install -m 0644 -D ${WORKDIR}/minibrowser.desktop ${D}${datadir}/applications/minibrowser.desktop
}

PACKAGECONFIG_vf = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', 'wayland' ,d)}"

ARM_INSTRUCTION_SET_vf = "thumb"

PACKAGE_ARCH_vf = "${MACHINE_ARCH}"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "file://minibrowser.desktop"

do_install_append() {
    install -m 0644 -D ${WORKDIR}/minibrowser.desktop ${D}${datadir}/applications/minibrowser.desktop
}

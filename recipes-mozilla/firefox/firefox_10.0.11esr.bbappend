FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "file://alignment.patch \
            file://distribution.ini \
            file://Prevent-SIGILL-crashes-on-ARMv6-builds-built-with-the-NDK_10.0esr.patch \
"

do_install_append() {
    install -d ${D}${libdir}/${PN}/distribution
    install -m 0644 ${WORKDIR}/distribution.ini ${D}${libdir}/${PN}/distribution/
}


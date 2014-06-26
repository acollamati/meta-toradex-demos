FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

#update to 10.0.12esr
PV = "10.0.12esr"
SRC_URI[archive.md5sum] = "ea6a9d240c271341eba014467b97e8ad"
SRC_URI[archive.sha256sum] = "9f12cb7fd18fc51e995cd67b70a090f32fcd8dcf636be1b840281f2612d9db40"

SRC_URI += "file://alignment.patch \
            file://distribution.ini \
            file://Prevent-SIGILL-crashes-on-ARMv6-builds-built-with-the-NDK_10.0esr.patch \
"

do_install_append() {
    install -d ${D}${libdir}/${PN}/distribution
    install -m 0644 ${WORKDIR}/distribution.ini ${D}${libdir}/${PN}/distribution/
}


FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://distribution.ini"

do_compile_append() {
    # disable annoying default browser check
    echo "lockPref(\"browser.shell.checkDefaultBrowser\", false);" >> ${WORKDIR}/vendor.js
}

do_compile_append_mx6() {
    # disable broken OMTC on iMX6 based modules
    echo "pref(\"layers.offmainthreadcomposition.enabled\", false);" >> ${WORKDIR}/vendor.js
}

do_compile_append_tegra() {
    # disable broken OMTC on T20/T30 based modules
    echo "pref(\"layers.offmainthreadcomposition.enabled\", false);" >> ${WORKDIR}/vendor.js
}

do_install_append() {
    install -d ${D}${libdir}/${PN}/distribution
    install -m 0644 ${WORKDIR}/distribution.ini ${D}${libdir}/${PN}/distribution/
}

FILES_${PN} += ""

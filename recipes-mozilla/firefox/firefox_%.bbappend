FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://distribution.ini"

do_compile_append() {
    # disable annoying default browser check
    echo "lockPref(\"browser.shell.checkDefaultBrowser\", false);" >> ${WORKDIR}/vendor.js
}

do_install_append() {
    install -d ${D}${libdir}/${PN}-${MOZ_APP_BASE_VERSION}/distribution
    install -m 0644 ${WORKDIR}/distribution.ini ${D}${libdir}/${PN}-${MOZ_APP_BASE_VERSION}/distribution/
}

FILES_${PN} += ""

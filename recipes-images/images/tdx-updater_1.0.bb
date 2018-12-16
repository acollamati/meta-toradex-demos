SUMMARY = "Toradex legacy updater host files"
DESCRIPTION = "Deploys files used by tdx-image-fstype.inc to create \
the legacy update tarball."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://update file://library"

do_install () {
    install -d ${D}/${datadir}/update
    cp -Lr ${WORKDIR}/update/* ${D}/${datadir}/update/
}

FILES_${PN} += "${datadir}/update"
PACKAGE_ARCH = "${MACHINE_ARCH}"

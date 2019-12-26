SUMMARY = "The Linux kernels spidev-test programm"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PACKAGE_ARCH = "${MACHINE_ARCH}"
S = "${WORKDIR}"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

do_compile () {
    ${CC} ${STAGING_KERNEL_DIR}/tools/spi/spidev_test.c -o spidev_test
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/spidev_test ${D}${bindir}/
}

SUMMARY = "The Linux kernels spidev-test programm"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${STAGING_KERNEL_DIR}/${KERNELPATH};endline=1;md5=fcab174c20ea2e2bc0be64b493708266"

PACKAGE_ARCH = "${MACHINE_ARCH}"
S = "${WORKDIR}"

do_configure[depends] += "virtual/kernel:do_shared_workdir"
do_populate_lic[depends] += "virtual/kernel:do_shared_workdir"

KERNELPATH = "tools/spi/spidev_test.c"

do_compile () {
    ${CC} ${LDFLAGS} ${STAGING_KERNEL_DIR}/${KERNELPATH} -o spidev_test
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/spidev_test ${D}${bindir}/
}

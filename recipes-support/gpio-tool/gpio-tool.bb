SUMMARY = "GPIOConfig tool for Toradex Modules"
SECTION = "base"
LICENSE = "CLOSED"
PR = "r3"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "gtk+"

SRC_URI = " \
    file://gpio-tool \
    file://gpio-tool.desktop \
    file://gpio-tool.png \
"

PACKAGES = "${PN}"

#no gnu_hash in binaries, skip QA dev-so for this package
#we have symlinks ending in .so, skip QA ldflags for this package
#inhibit warnings about files being stripped
INSANE_SKIP_${PN} = "ldflags already-stripped"

# just don't do any configuring
do_configure() {
}

do_install() {
    install -d ${D}/${bindir}
    install -d ${D}/${datadir}/applications
    install -d ${D}/${datadir}/pixmaps
    install -m 755 ${WORKDIR}/gpio-tool ${D}/${bindir}/
    install -m 644 ${WORKDIR}/gpio-tool.desktop ${D}/${datadir}/applications/
    install -m 644 ${WORKDIR}/gpio-tool.png ${D}/${datadir}/pixmaps/
}

pkg_postinst_${PN}() {
    mkdir -p ${base_prefix}/home/root/Desktop
    cp ${datadir}/applications/gpio-tool.desktop ${base_prefix}/home/root/Desktop/
}

pkg_postrm_${PN}() {
    rm -f ${base_prefix}/home/Desktop/gpio-tool.desktop
}

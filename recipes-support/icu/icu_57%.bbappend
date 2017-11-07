# use a customized small icu data library created from
# http://apps.icu-project.org/datacustom/ICUData53.html
#   Charset Mapping Tables (only minimum set)
#   Break Iterator (en_US)
#   Base Data

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_colibri-imx6ull = "${BASE_SRC_URI} file://icudt57l.zip"
SRC_URI_colibri-vf = "${BASE_SRC_URI} file://icudt57l.zip"

inject_small_database () {
    rm  -f ${S}/data/in/icudt*l.dat
    cp ${WORKDIR}/icudt*l.dat ${S}/data/in/
}

do_configure_append_colibri-imx6ull () {
    inject_small_database
}

do_configure_append_colibri-vf () {
    inject_small_database
}

PACKAGE_ARCH_colibri-imx6ull = "${MACHINE_ARCH}"
PACKAGE_ARCH_colibri-vf = "${MACHINE_ARCH}"

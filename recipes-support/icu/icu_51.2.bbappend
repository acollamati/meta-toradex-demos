# use a customized small icu data library created from
# http://apps.icu-project.org/datacustom/ICUData51.html
#   Charset Mapping Tables
#   Base Data
PRINC_colibri-vf = "1"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_colibri-vf = "${BASE_SRC_URI} file://icudt51l.zip "

do_configure_append_colibri-vf () {
    rm  ${S}/data/in/icudt51l.dat
    cp ${WORKDIR}/icudt51l.dat ${S}/data/in/
}

PACKAGE_ARCH_colibri-vf = "${MACHINE_ARCH}"

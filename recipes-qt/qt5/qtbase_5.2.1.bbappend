PACKAGECONFIG_GL_tegra = "gles2"
PACKAGECONFIG_EXAMPLES ?= "examples"

PACKAGECONFIG_append_tegra += " \
    ${PACKAGECONFIG_EXAMPLES} \
"

PACKAGECONFIG_append_vf += " \
    ${PACKAGECONFIG_EXAMPLES} \
"
# Workaround from the Fedora ARM port preventing the following compiler error
# happening with the Linaro 4.8. 2014.04 toolchain
# qtbase-opensource-src-5.2.1/src/tools/qdoc/quoter.cpp:139:1: internal compiler error: in add_stores, at var-tracking.c:5918
#
# https://bugs.linaro.org/show_bug.cgi?id=534
# http://pkgs.fedoraproject.org/cgit/mingw-qt5-qtbase.git/tree/qt5-workaround-gcc48-arm-build-failure.patch?h=f20
FILESEXTRAPATHS_prepend := "${THISDIR}/files/:"
SRC_URI += "file://0017-qt5-workaround-gcc48-arm-build-failure.patch"

FILESEXTRAPATHS_prepend := "${THISDIR}/qt4:"

SRC_URI += " file://0001-QWS-fix-24-bit-RGB-BGR-handling.patch"
QT_CONFIG_FLAGS += " -depths all"

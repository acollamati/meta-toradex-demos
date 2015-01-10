FILESEXTRAPATHS_prepend := "${THISDIR}/connman:"

SRC_URI += " \
    file://dont_start_connman_on_nfsboot.patch \
"

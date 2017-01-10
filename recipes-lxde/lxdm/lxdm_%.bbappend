FILESEXTRAPATHS_prepend := "${THISDIR}/lxdm:"

SRC_URI += " \
    file://logout-fixes.patch \
    file://root-autologin.patch \
"

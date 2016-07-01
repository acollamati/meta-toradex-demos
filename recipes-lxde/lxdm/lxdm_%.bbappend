FILESEXTRAPATHS_prepend := "${THISDIR}/lxdm:"

SRC_URI += " \
    file://logout-fixes.patch \
    file://root-autologin.patch \
    ${@bb.utils.contains("DISTRO_TYPE", "debug", "", "file://0001-lxdm.conf.in-blacklist-root-for-release-images.patch",d)} \
"

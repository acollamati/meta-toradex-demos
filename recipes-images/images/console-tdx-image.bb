SUMMARY = "Toradex Embedded Linux Console Demo"
SUMMARY_append_apalis-tk1-mainline = " (Mainline)"
DESCRIPTION = "Image without graphical interface"

LICENSE = "MIT"

#start of the resulting deployable tarball name
export IMAGE_BASENAME = "Console-Image"
IMAGE_NAME_apalis-imx6 = "Apalis-iMX6_${IMAGE_BASENAME}"
IMAGE_NAME_apalis-tk1 = "Apalis-TK1_${IMAGE_BASENAME}"
IMAGE_NAME_apalis-tk1-mainline = "Apalis-TK1-Mainline_${IMAGE_BASENAME}"
IMAGE_NAME_colibri-imx6 = "Colibri-iMX6_${IMAGE_BASENAME}"
IMAGE_NAME_colibri-imx6ull = "Colibri-iMX6ULL_${IMAGE_BASENAME}"
IMAGE_NAME_colibri-imx7 = "Colibri-iMX7_${IMAGE_BASENAME}"
IMAGE_NAME_colibri-imx7-emmc = "Colibri-iMX7-eMMC_${IMAGE_BASENAME}"
IMAGE_NAME_colibri-pxa = "Colibri-PXA_${IMAGE_BASENAME}"
IMAGE_NAME = "${MACHINE}_${IMAGE_BASENAME}"

SYSTEMD_DEFAULT_TARGET = "graphical.target"

IMAGE_FEATURES += " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'x11', \
                                                       '', d), d)} \
"

IMAGE_LINGUAS = "en-us"
#IMAGE_LINGUAS = "de-de fr-fr en-gb en-us pt-br es-es kn-in ml-in ta-in"
#ROOTFS_POSTPROCESS_COMMAND += 'install_linguas; '

DISTRO_UPDATE_ALTERNATIVES ??= ""
ROOTFS_PKGMANAGE_PKGS ?= '${@oe.utils.conditional("ONLINE_PACKAGE_MANAGEMENT", "none", "", "${ROOTFS_PKGMANAGE} ${DISTRO_UPDATE_ALTERNATIVES}", d)}'

CONMANPKGS ?= "connman connman-plugin-loopback connman-plugin-ethernet connman-plugin-wifi connman-client"

IMAGE_INSTALL += " \
    packagegroup-boot \
    packagegroup-basic \
    udev-extra-rules \
    ${CONMANPKGS} \
    ${ROOTFS_PKGMANAGE_PKGS} \
    timestamp-service \
    packagegroup-base-extended \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'xterm', '', d)} \
"

require recipes-images/images/tdx-extra.inc

IMAGE_DEV_MANAGER   = "udev"
IMAGE_INIT_MANAGER  = "systemd"
IMAGE_INITSCRIPTS   = " "
IMAGE_LOGIN_MANAGER = "busybox shadow"

inherit core-image

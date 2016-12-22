SUMMARY = "Toradex Embedded Linux Console Demo"

LICENSE = "MIT"

PV = "V2.7"

#start of the resulting deployable tarball name
IMAGE_NAME_apalis-imx6 = "Apalis_iMX6_LinuxConsoleImage"
IMAGE_NAME_apalis-t30 = "Apalis_T30_LinuxConsoleImage"
IMAGE_NAME_apalis-tk1 = "Apalis_TK1_LinuxConsoleImage"
IMAGE_NAME_apalis-tk1-mainline = "Apalis_TK1_Mainline_LinuxConsoleImage"
IMAGE_NAME_colibri-imx6 = "Colibri_iMX6_LinuxConsoleImage"
IMAGE_NAME_colibri-imx7 = "Colibri_iMX7_LinuxConsoleImage"
IMAGE_NAME_colibri-pxa = "Colibri_PXA_LinuxConsoleImage"
IMAGE_NAME_colibri-t20 = "Colibri_T20_LinuxConsoleImage"
IMAGE_NAME_colibri-t30 = "Colibri_T30_LinuxConsoleImage"
IMAGE_NAME_colibri-vf = "Colibri_VF_LinuxConsoleImage"
IMAGE_NAME = "${MACHINE}_LinuxConsoleImage"

#create the deployment directory-tree
require recipes-images/images/trdx-image-fstype.inc

IMAGE_LINGUAS = "en-us"
#IMAGE_LINGUAS = "de-de fr-fr en-gb en-us pt-br es-es kn-in ml-in ta-in"
#ROOTFS_POSTPROCESS_COMMAND += 'install_linguas; '

DISTRO_UPDATE_ALTERNATIVES ??= ""
ROOTFS_PKGMANAGE_PKGS ?= '${@base_conditional("ONLINE_PACKAGE_MANAGEMENT", "none", "", "${ROOTFS_PKGMANAGE} ${DISTRO_UPDATE_ALTERNATIVES}", d)}'

CONMANPKGS ?= "connman connman-systemd connman-plugin-loopback connman-plugin-ethernet connman-plugin-wifi connman-client"
CONMANPKGS_libc-uclibc = ""

#don't install some id databases
#BAD_RECOMMENDATIONS_append_colibri-vf += " udev-hwdb cpufrequtils "

#deploy the X server for the tegras
#this adds a few MB to the image, but all graphical HW acceleration is
#available only on top of X, this is not required for nouveau based build.
IMAGE_INSTALL_append_tegra = " ${XSERVER} xterm xclock"
IMAGE_INSTALL_append_tegra124 = " ${XSERVER} xterm xclock"

IMAGE_INSTALL += " \
    angstrom-packagegroup-boot \
    packagegroup-basic \
    udev-extra-rules \
    ${CONMANPKGS} \
    ${ROOTFS_PKGMANAGE_PKGS} \
    timestamp-service \
    packagegroup-base-extended \
"

require recipes-images/images/trdx-extra.inc

IMAGE_DEV_MANAGER   = "udev"
IMAGE_INIT_MANAGER  = "systemd"
IMAGE_INITSCRIPTS   = " "
IMAGE_LOGIN_MANAGER = "busybox shadow"

export IMAGE_BASENAME = "console-trdx-image"

inherit image

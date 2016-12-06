SUMMARY = "Toradex Embedded Linux Qt4 Demo With QWS"

LICENSE = "MIT"

PV = "V2.6.2"

#start of the resulting deployable tarball name
IMAGE_NAME_colibri-pxa = "Colibri_PXA_LinuxImage-qte"
IMAGE_NAME_colibri-vf = "Colibri_VF_LinuxImage-qte"
IMAGE_NAME = "${MACHINE}_LinuxImage-qte"

#start graphical target by default (start new qt-demo-init systemd service)
SYSTEMD_DEFAULT_TARGET = "graphical.target"

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

IMAGE_INSTALL += " \
    packagegroup-core-qt4e \
    libicui18n \
    \
    initscripts \
    polkit \
    makedevs \
    mime-support \
    zeroconf \
    fbset \	
    angstrom-packagegroup-boot \
    packagegroup-basic \
    udev-extra-rules \
    ${CONMANPKGS} \
    ${ROOTFS_PKGMANAGE_PKGS} \
    timestamp-service \
    packagegroup-base-extended \
    \
    bash \
    \
    alsa-states \
"

require recipes-images/images/trdx-extra.inc

IMAGE_DEV_MANAGER   = "udev"
IMAGE_INIT_MANAGER  = "systemd"
IMAGE_INITSCRIPTS   = " "
IMAGE_LOGIN_MANAGER = "busybox shadow"

export IMAGE_BASENAME = "qt-image"

inherit core-image

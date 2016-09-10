#Angstrom image
SUMMARY = "Image based on the LXDE desktop"

LICENSE = "MIT"

PV = "V2.6.1"

#start of the resulting deployable tarball name
IMAGE_NAME_apalis-imx6 = "Apalis_iMX6_LinuxImage"
IMAGE_NAME_apalis-t30 = "Apalis_T30_LinuxImage"
IMAGE_NAME_apalis-tk1 = "Apalis_TK1_LinuxImage"
IMAGE_NAME_colibri-imx6 = "Colibri_iMX6_LinuxImage"
IMAGE_NAME_colibri-imx7 = "Colibri_iMX7_LinuxImage"
IMAGE_NAME_colibri-pxa = "Colibri_PXA_LinuxImage"
IMAGE_NAME_colibri-t20 = "Colibri_T20_LinuxImage"
IMAGE_NAME_colibri-t30 = "Colibri_T30_LinuxImage"
IMAGE_NAME_colibri-vf = "Colibri_VF_LinuxImage"
IMAGE_NAME = "${MACHINE}_LinuxImage"

SYSTEMD_DEFAULT_TARGET = "graphical.target"

#create the deployment directory-tree
require recipes-images/images/trdx-image-fstype.inc

#remove interfering sysv scripts, connman systemd service
do_mkrmscript () {
    echo "for i in ${IMAGE_ROOTFS}/etc/rc0.d ${IMAGE_ROOTFS}/etc/rc1.d ${IMAGE_ROOTFS}/etc/rc2.d ${IMAGE_ROOTFS}/etc/rc3.d ${IMAGE_ROOTFS}/etc/rc4.d ${IMAGE_ROOTFS}/etc/rc5.d ${IMAGE_ROOTFS}/etc/rc6.d ${IMAGE_ROOTFS}/etc/rcS.d ; do" > ${WORKDIR}/rmscript
    echo "    rm -f \$i/*dropbear \$i/*avahi-daemon \$i/*dbus-1 \$i/*lxdm \$i/*ntpd \$i/*syslog \$i/*ofono \$i/*alsa-state \$i/*networking \$i/*udev-late-mount \$i/*sendsigs \$i/*save-rtc.sh \$i/*umountnfs.sh \$i/*portmap \$i/*umountfs \$i/*halt \$i/*rmnologin.sh \$i/*reboot; rm -f \$i/*banner.sh \$i/*sysfs.sh \$i/*checkroot.sh \$i/*alignment.sh \$i/*mountall.sh \$i/*populate-volatile.sh \$i/*devpts.sh \$i/*hostname.sh \$i/*portmap \$i/*mountnfs.sh \$i/*bootmisc.sh" >> ${WORKDIR}/rmscript
    echo "done" >> ${WORKDIR}/rmscript
    chmod +x ${WORKDIR}/rmscript
    readlink -e ${WORKDIR}/rmscript
    cat ${WORKDIR}/rmscript
}
addtask mkrmscript after do_rootfs before do_imagedeploy

IMAGE_LINGUAS = "en-us"
#IMAGE_LINGUAS = "de-de fr-fr en-gb en-us pt-br es-es kn-in ml-in ta-in"
#ROOTFS_POSTPROCESS_COMMAND += 'install_linguas; '

DISTRO_UPDATE_ALTERNATIVES ??= ""
ROOTFS_PKGMANAGE_PKGS ?= '${@base_conditional("ONLINE_PACKAGE_MANAGEMENT", "none", "", "${ROOTFS_PKGMANAGE} ${DISTRO_UPDATE_ALTERNATIVES}", d)}'

CONMANPKGS ?= "connman connman-client connman-gnome"
CONMANPKGS_libc-uclibc = ""

DEPENDS_append_tegra = " gst-plugins-good gst-plugins-bad gst-plugins-ugly"
#do not build plugins-ugly because it would require to whitelist LICENCES without deploying them
DEPENDS += "gstreamer1.0-plugins-good gstreamer1.0-plugins-bad"
DEPENDS_remove_tegra = "gstreamer1.0-plugins-good gstreamer1.0-plugins-bad"

#deploy the OpenGL ES headers to the sysroot
DEPENDS_append_tegra = " nvsamples"

IMAGE_BROWSER = "firefox"
#keep the rootfs size small
IMAGE_BROWSER_colibri-vf = ""

# don't install some packages bloating the vybrid image
BAD_RECOMMENDATIONS_append_colibri-vf = " udev-hwdb cpufrequtils"

# don't install a second icon theme
BAD_RECOMMENDATIONS_append = " adwaita-icon-theme adwaita-icon-theme-symbolic"

# this would pull in a large amount of gst-plugins, we only add a selected few
#    gstreamer1.0-plugins-base-meta
#    gstreamer1.0-plugins-good-meta
#    gstreamer1.0-plugins-bad-meta
#    gst-ffmpeg
GSTREAMER = " \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-base-alsa \
    gstreamer1.0-plugins-base-audioconvert \
    gstreamer1.0-plugins-base-audioresample \
    gstreamer1.0-plugins-base-audiotestsrc \
    gstreamer1.0-plugins-base-typefindfunctions \
    gstreamer1.0-plugins-base-ivorbisdec \
    gstreamer1.0-plugins-base-ogg \
    gstreamer1.0-plugins-base-theora \
    gstreamer1.0-plugins-base-videotestsrc \
    gstreamer1.0-plugins-base-vorbis \
    gstreamer1.0-plugins-good-audioparsers \
    gstreamer1.0-plugins-good-autodetect \
    gstreamer1.0-plugins-good-avi \
    gstreamer1.0-plugins-good-deinterlace \
    gstreamer1.0-plugins-good-id3demux \
    gstreamer1.0-plugins-good-isomp4 \
    gstreamer1.0-plugins-good-matroska \
    gstreamer1.0-plugins-good-rtp \
    gstreamer1.0-plugins-good-rtpmanager \
    gstreamer1.0-plugins-good-udp \
    gstreamer1.0-plugins-good-video4linux2 \
    gstreamer1.0-plugins-good-wavenc \
    gstreamer1.0-plugins-good-wavparse \
"
# No longer available
#    gst-plugins-base-decodebin \
#    gst-plugins-base-decodebin2 \
#    gst-plugins-base-playbin \
#    gst-plugins-ugly-asf \
#"

GSTREAMER_append_mx6 = " \
    gstreamer1.0-plugins-base-ximagesink \
    gstreamer1.0-plugins-imx \
    gst1.0-fsl-plugin \
    gst1.0-fsl-plugin-gplay \
    gst1.0-fsl-plugin-grecorder \
"
GSTREAMER_append_mx7 = " \
    gstreamer1.0-plugins-base-ximagesink \
    gst1.0-fsl-plugin \
"
# No longer available
#    gst-plugins-gl \
#    gst-fsl-plugin \
#

# use gstreamer-0.10 for tegra
GSTREAMER_tegra = " \
    gstreamer \
    gst-plugins-base \
    gst-plugins-base-alsa \
    gst-plugins-base-audioconvert \
    gst-plugins-base-audioresample \
    gst-plugins-base-audiotestsrc \
    gst-plugins-base-decodebin \
    gst-plugins-base-decodebin2 \
    gst-plugins-base-playbin \
    gst-plugins-base-typefindfunctions \
    gst-plugins-base-ivorbisdec \
    gst-plugins-base-ogg \
    gst-plugins-base-theora \
    gst-plugins-base-videotestsrc \
    gst-plugins-base-vorbis \
    gst-plugins-good-audioparsers \
    gst-plugins-good-autodetect \
    gst-plugins-good-avi \
    gst-plugins-good-deinterlace \
    gst-plugins-good-id3demux \
    gst-plugins-good-isomp4 \
    gst-plugins-good-matroska \
    gst-plugins-good-rtp \
    gst-plugins-good-rtpmanager \
    gst-plugins-good-udp \
    gst-plugins-good-video4linux2 \
    gst-plugins-good-wavenc \
    gst-plugins-good-wavparse \
    gst-plugins-ugly-asf \
"
GSTREAMER_append_tegra3 = " \
    gst-plugins-good-jpeg \
"
GSTREAMER_append_tegra124 = " \
    gstreamer1.0-libav \
    gstreamer1.0-plugins-bad-videoparsersbad \
    libgstcodecparsers-1.0 \
    libgstnvegl \
    libgstomx \
"
GSTREAMER_colibri-vf = ""

IMAGE_INSTALL_append_tegra = " \
    gpioconfig \
    tegrastats-gtk \
    gnome-disk-utility \
    mime-support \
    eglinfo-x11 \
    xvinfo \
"
IMAGE_INSTALL_append_tegra124 = " \
    eglinfo-x11 \
    gnome-disk-utility \
    libglu \
    mesa-glut \
    mime-support \
    tiff \
    xvinfo \
"
IMAGE_INSTALL_append_mx6 = " \
    gpioconfig \
    packagegroup-fsl-tools-gpu \
    gnome-disk-utility \
    mime-support \
    eglinfo-x11 \
"
IMAGE_INSTALL_append_mx7 = " \
    gnome-disk-utility \
    mime-support \
"
IMAGE_INSTALL_append_vf = " \
    gpioconfig \
    xf86-video-modesetting \
"

# Packages which might be empty or no longer available
RRECOMMENDS_${PN} += " \
    xserver-xorg-multimedia-modules \
    xserver-xorg-extension-dbe \
    xserver-xorg-extension-extmod \
"

IMAGE_INSTALL += " \
    gconf \
    gnome-vfs \
    gnome-vfs-plugin-file \
    gvfs \
    gvfsd-trash \
    xdg-utils \
    \
    initscripts \
    libgsf \
    libwnck \
    libxres \
    makedevs \
    xcursor-transparent-theme \
    zeroconf \
    angstrom-packagegroup-boot \
    packagegroup-basic \
    udisks \
    udev-extra-rules \
    ${CONMANPKGS} \
    ${ROOTFS_PKGMANAGE_PKGS} \
    timestamp-service \
    packagegroup-base-extended \
    ${XSERVER} \
    xserver-common \
    xauth \
    xhost \
    xset \
    setxkbmap \
    \
    xrdb \
    xorg-minimal-fonts xserver-xorg-utils \
    scrot \
    unclutter \
    \
    libxdamage libxvmc libxinerama \
    libxcursor \
    \
    florence \
    bash \
    \
    ${GSTREAMER} \
    v4l-utils \
    libpcre \
    libpcreposix \
    libxcomposite \
    alsa-states \
    ${IMAGE_BROWSER} \
"
require recipes-images/images/lx.inc
require recipes-images/images/trdx-extra.inc

IMAGE_DEV_MANAGER   = "udev"
IMAGE_INIT_MANAGER  = "systemd"
IMAGE_INITSCRIPTS   = " "
IMAGE_LOGIN_MANAGER = "busybox shadow"

export IMAGE_BASENAME = "LXDE-image"

inherit core-image

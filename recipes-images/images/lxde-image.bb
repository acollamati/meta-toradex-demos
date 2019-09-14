SUMMARY = "Toradex Embedded Linux Demo with LXDE"
SUMMARY_append_apalis-tk1-mainline = " (Mainline)"
DESCRIPTION = "Image with the LXDE desktop environment"

LICENSE = "MIT"

inherit core-image

# Most LXDE components don't even compile without native X11 support,
# so make this fail early.
COMPATIBLE_MACHINE_mx8 = "(^$)"

#start of the resulting deployable tarball name
export IMAGE_BASENAME = "LXDE-Image"
MACHINE_NAME ?= "${MACHINE}"
IMAGE_NAME = "${MACHINE_NAME}_${IMAGE_BASENAME}"

SYSTEMD_DEFAULT_TARGET = "graphical.target"

IMAGE_FEATURES += " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'x11', \
                                                       '', d), d)} \
"

IMAGE_LINGUAS = "en-us"
#IMAGE_LINGUAS = "de-de fr-fr en-gb en-us pt-br es-es kn-in ml-in ta-in"
#ROOTFS_POSTPROCESS_COMMAND += 'install_linguas; '

ROOTFS_PKGMANAGE_PKGS ?= '${@oe.utils.conditional("ONLINE_PACKAGE_MANAGEMENT", "none", "", "${ROOTFS_PKGMANAGE}", d)}'

CONMANPKGS ?= "connman connman-client connman-gnome"

IMAGE_BROWSER = "firefox"
#keep the rootfs size small
IMAGE_BROWSER_colibri-imx6ull = ""

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
    gstreamer1.0-plugins-good-multifile \
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

GSTREAMER_MX6QDL = " \
    gstreamer1.0-plugins-base-ximagesink \
    gstreamer1.0-plugins-imx \
"
GSTREAMER_append_mx6q = "${GSTREAMER_MX6QDL}"
GSTREAMER_append_mx6dl = "${GSTREAMER_MX6QDL}"

GSTREAMER_colibri-imx6ull = ""

GSTREAMER_append_mx7 = " \
    gstreamer1.0-plugins-base-ximagesink \
    imx-gst1.0-plugin \
"
# No longer available
#    gst-plugins-gl \
#    gst-fsl-plugin \
#

GSTREAMER_append_tegra124 = " \
    ${@bb.utils.contains("LICENSE_FLAGS_WHITELIST", "commercial", "gstreamer1.0-libav", "", d)} \
    gstreamer1.0-plugins-bad-videoparsersbad \
    libgstcodecparsers-1.0 \
    libgstnvegl \
    libgstomx-1.0 \
"
IMAGE_INSTALL_append_tegra124 = " \
    gpio-tool \
    gnome-disk-utility \
    libglu \
    mesa-demos \
    freeglut \
    mime-support \
    tiff \
    xvinfo \
    \
"
IMAGE_INSTALL_append_tegra124m = " \
    gpio-tool \
    gnome-disk-utility \
    libglu \
    mesa-demos \
    freeglut \
    mime-support \
    tiff \
    xvinfo \
    \
"
IMAGE_INSTALL_MX6QDL = " \
    gpio-tool \
    packagegroup-fsl-gpu-libs \
    libopencl-imx \
    gnome-disk-utility \
    mime-support \
"
IMAGE_INSTALL_append_mx6q = "${IMAGE_INSTALL_MX6QDL}"
IMAGE_INSTALL_append_mx6dl = "${IMAGE_INSTALL_MX6QDL}"

IMAGE_INSTALL_append_mx7 = " \
    gpio-tool \
    gnome-disk-utility \
    mime-support \
"
IMAGE_INSTALL_append_colibri-imx6ull = " \
    gpio-tool \
"

# Packages which might be empty or no longer available
RRECOMMENDS_${PN} += " \
    xserver-xorg-multimedia-modules \
    xserver-xorg-extension-dbe \
    xserver-xorg-extension-extmod \
"

IMAGE_INSTALL += " \
    eject \
    gconf \
    gvfs \
    gvfsd-trash \
    xdg-utils \
    \
    libgsf \
    libxres \
    makedevs \
    xcursor-transparent-theme \
    zeroconf \
    packagegroup-boot \
    packagegroup-basic \
    udev-extra-rules \
    ${CONMANPKGS} \
    ${ROOTFS_PKGMANAGE_PKGS} \
    timestamp-service \
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
#    gnome-vfs \
#    gnome-vfs-plugin-file \
#    florence3 \
#

require recipes-images/images/lx.inc
require recipes-images/images/tdx-extra.inc

IMAGE_DEV_MANAGER   = "udev"
IMAGE_INIT_MANAGER  = "systemd"
IMAGE_INITSCRIPTS   = " "
IMAGE_LOGIN_MANAGER = "busybox shadow"

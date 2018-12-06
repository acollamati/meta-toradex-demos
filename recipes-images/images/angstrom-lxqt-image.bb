SUMMARY = "Toradex Embedded Linux Demo with LXQT"
SUMMARY_append_apalis-tk1-mainline = " (Mainline)"
DESCRIPTION = "Angstrom-based image with the LXQT desktop environment"

LICENSE = "MIT"

#start of the resulting deployable tarball name
export IMAGE_BASENAME = "LXQt-Image"
IMAGE_NAME_apalis-imx6 = "Apalis-iMX6_${IMAGE_BASENAME}"
IMAGE_NAME_apalis-t30 = "Apalis-T30_${IMAGE_BASENAME}"
IMAGE_NAME_apalis-tk1 = "Apalis-TK1_${IMAGE_BASENAME}"
IMAGE_NAME_apalis-tk1-mainline = "Apalis-TK1-Mainline_${IMAGE_BASENAME}"
IMAGE_NAME_colibri-imx6 = "Colibri-iMX6_${IMAGE_BASENAME}"
IMAGE_NAME_colibri-imx6ull = "Colibri-iMX6ULL_${IMAGE_BASENAME}"
IMAGE_NAME_colibri-imx7 = "Colibri-iMX7_${IMAGE_BASENAME}"
IMAGE_NAME_colibri-imx7-emmc = "Colibri-iMX7-eMMC_${IMAGE_BASENAME}"
IMAGE_NAME_colibri-pxa = "Colibri-PXA_${IMAGE_BASENAME}"
IMAGE_NAME_colibri-t20 = "Colibri-T20_${IMAGE_BASENAME}"
IMAGE_NAME_colibri-t30 = "Colibri-T30_${IMAGE_BASENAME}"
IMAGE_NAME_colibri-vf = "Colibri-VF_${IMAGE_BASENAME}"
IMAGE_NAME = "${MACHINE}_${IMAGE_BASENAME}"

SYSTEMD_DEFAULT_TARGET = "graphical.target"

#create the deployment directory-tree
require recipes-images/images/tdx-image-fstype.inc

inherit populate_sdk populate_sdk_qt5

IMAGE_LINGUAS = "en-us"
#IMAGE_LINGUAS = "de-de fr-fr en-gb en-us pt-br es-es kn-in ml-in ta-in"
#ROOTFS_POSTPROCESS_COMMAND += 'install_linguas; '

DISTRO_UPDATE_ALTERNATIVES ??= ""
ROOTFS_PKGMANAGE_PKGS ?= '${@oe.utils.conditional("ONLINE_PACKAGE_MANAGEMENT", "none", "", "${ROOTFS_PKGMANAGE} ${DISTRO_UPDATE_ALTERNATIVES}", d)}'

# for a more complete configuration tool consider replacing lxqt-connman-applet
# with cmst at the price of a bigger footprint
CONMANPKGS ?= "connman connman-client lxqt-connman-applet"

#deploy the OpenGL ES headers to the sysroot
DEPENDS_append_tegra = " nvsamples"

IMAGE_BROWSER = ""
#keep the rootfs size small
IMAGE_BROWSER_colibri-imx6ull = ""
IMAGE_BROWSER_colibri-vf = ""

# don't install some packages bloating the vybrid image
BAD_RECOMMENDATIONS_append_colibri-vf = " udev-hwdb cpufrequtils"

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

GSTREAMER_MX6QDL = " \
    gstreamer1.0-plugins-base-ximagesink \
    gstreamer1.0-plugins-imx \
    imx-gst1.0-plugin \
    imx-gst1.0-plugin-gplay \
    imx-gst1.0-plugin-grecorder \
"
GSTREAMER_append_mx6q = "${GSTREAMER_MX6QDL}"
GSTREAMER_append_mx6dl = "${GSTREAMER_MX6QDL}"

GSTREAMER_colibri-imx6ull = ""

GSTREAMER_append_mx7 = " \
    gstreamer1.0-plugins-base-ximagesink \
    imx-gst1.0-plugin \
"

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
    gst-plugins-base-ogg \
    gst-plugins-base-theora \
    gst-plugins-base-videotestsrc \
    gst-plugins-base-vorbis \
    gst-plugins-base-ximagesink \
    gst-plugins-base-xvimagesink \
    gst-plugins-good \
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
    libgstcodecparsers-1.0 \
    libgstnvegl \
    libgstomx-0.10 \
    libgstomx-1.0 \
"
GSTREAMER_colibri-vf = ""

IMAGE_INSTALL_append_tegra = " \
    mime-support \
    eglinfo-x11 \
    xvinfo \
"
IMAGE_INSTALL_append_tegra124 = " \
    libglu \
    mesa-demos \
    freeglut \
    mime-support \
    tiff \
    xvinfo \
"
IMAGE_INSTALL_append_tegra124m = " \
    libglu \
    mesa-demos \
    freeglut \
    mime-support \
    tiff \
    xvinfo \
"
IMAGE_INSTALL_MX6QDL = " \
    packagegroup-fsl-gpu-libs \
    mime-support \
    eglinfo-x11 \
"
IMAGE_INSTALL_append_mx6q = "${IMAGE_INSTALL_MX6QDL}"
IMAGE_INSTALL_append_mx6dl = "${IMAGE_INSTALL_MX6QDL}"

IMAGE_INSTALL_append_mx7 = " \
    mime-support \
"
IMAGE_INSTALL_append_vf = " \
    xf86-video-modesetting \
"

# Packages which might be empty or no longer available
RRECOMMENDS_${PN} += " \
    xserver-xorg-multimedia-modules \
    xserver-xorg-extension-dbe \
    xserver-xorg-extension-extmod \
"

# deploy these unconditionally, so that they are there for development/debugging
# for a final image this is unnedded and the applications will pull in whatever needed
# through their RRECOMMENDS

# check the licensing at http://doc.qt.io/qt-5/licensing.html
QT5_LIBS ?= " \
    qt3d \
    qt5-plugin-generic-vboxtouch \
    qtbase \
    qtcanvas3d \
    qtconnectivity \
    qtdeclarative \
    qtenginio \
    qtgraphicaleffects \
    qtimageformats \
    qtlocation \
    qtmultimedia \
    qtquick1 \
    qtquickcontrols2 \
    qtquickcontrols \
    qtscript \
    qtsensors \
    qtserialport \
    qtsvg \
    qtsystems \
    qttools \
    qttranslations \
    qtwebchannel \
    qtwebkit-examples \
    qtwebkit \
    qtwebsockets \
    qtxmlpatterns \
"
QT5_LIBS_GPLv3 ?= " \
    qtcharts \
    qtdatavis3d \
    qtvirtualkeyboard \
"

IMAGE_INSTALL += " \
    eject \
    gvfs \
    gvfsd-trash \
    xdg-utils \
    \
    libgsf \
    libxres \
    makedevs \
    xcursor-transparent-theme \
    zeroconf \
    angstrom-packagegroup-boot \
    packagegroup-basic \
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
    bash \
    \
    ${GSTREAMER} \
    v4l-utils \
    libpcre \
    libpcreposix \
    libxcomposite \
    alsa-states \
    ${IMAGE_BROWSER} \
    sddm \
    ${QT5_LIBS} \
    ${QT5_LIBS_GPLv3} \
    packagegroup-lxqt-base \
    qedit \
    liberation-fonts \
"
require recipes-images/images/tdx-extra.inc

IMAGE_DEV_MANAGER   = "udev"
IMAGE_INIT_MANAGER  = "systemd"
IMAGE_INITSCRIPTS   = " "
IMAGE_LOGIN_MANAGER = "busybox shadow"

inherit core-image

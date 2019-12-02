require graphical-tdx-image.bb

SUMMARY = "Toradex Embedded Linux Verification Image"
SUMMARY_append_apalis-tk1-mainline = " (Mainline)"
DESCRIPTION = "Image for BSP verification"

LICENSE = "MIT"

#Prefix to the resulting deployable tarball name
export IMAGE_BASENAME = "Verification-Image"

IMAGE_INSTALL += " \
    packagegroup-tdx-cli \
    packagegroup-tdx-graphical \
    \
    bash \
    coreutils \
    less \
    makedevs \
    mime-support \
    util-linux \
    v4l-utils \
    \
    gpicview \
    media-files \
"

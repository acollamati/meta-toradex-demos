require tdx-reference-minimal-image.bb

SUMMARY = "Toradex Embedded Linux Reference Multimedia Image"
DESCRIPTION = "Image for BSP verification with QT and multimedia features"

#Prefix to the resulting deployable tarball name
export IMAGE_BASENAME = "Reference-Multimedia-Image"

SYSTEMD_DEFAULT_TARGET = "graphical.target"

IMAGE_FEATURES += " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'x11', \
                                                       '', d), d)} \
"

IMAGE_INSTALL += " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', \
                         'weston weston-init weston-examples wayland-terminal-launch', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11 wayland', \
                         'weston-xwayland xterm', \
       bb.utils.contains('DISTRO_FEATURES', 'x11', 'x-window-xterm', '', d), d)} \
    \
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

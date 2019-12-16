SUMMARY = "Media Files for tests"
LICENSE = "CC0-1.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/CC0-1.0;md5=0ceb3372c9595f0a8067e55da801e4a1"

inherit allarch bin_package

SRC_URI = " \
    https://developer1.toradex.com/files/toradex-dev/uploads/media/Colibri/AddSW/Linux/ReleaseTest/media-files.tar.xz \
"
SRC_URI[md5sum] = "efa7dc8bedcec877cdb0a5ea6afc5ec0"
SRC_URI[sha256sum] = "7757f4ae30c72966a2104b8ab0192c0b76659e8b57afbbae20839990d71d327e"

S = "${WORKDIR}/media-files"

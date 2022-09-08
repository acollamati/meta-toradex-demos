SUMMARY = "Media Files for tests"
LICENSE = "CC0-1.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/CC0-1.0;md5=0ceb3372c9595f0a8067e55da801e4a1"

inherit allarch bin_package

SRC_URI = " \
    https://developer1.toradex.com/files/toradex-dev/uploads/media/Colibri/AddSW/Linux/ReleaseTest/media-files-${PV}.tar.xz \
"
SRC_URI[md5sum] = "74420d7d46a19ec232e21059b8eefd85"
SRC_URI[sha256sum] = "8f99b76d56464bafe3cac241fc711b5739236753add55be7a88f28c870e1d013"

S = "${WORKDIR}/media-files"

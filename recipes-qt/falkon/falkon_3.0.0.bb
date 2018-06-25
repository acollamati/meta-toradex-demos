SUMMARY = "Falkon Webbrowser"
HOMEPAGE = "http://www.falkon.org"
SECTION = "x11"

LICENSE = "GPLv3 & LGPLv3 & MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=8f0e2cd40e05189ec81232da84bd6e1a"

DEPENDS  = "libxcb openssl qtbase qttools-native qtwebengine qtx11extras"

inherit cmake_qt5_extra kde-base

SRC_URI = "https://download.kde.org/stable/falkon/3.0/src/falkon-3.0.0.tar.xz"
SRC_URI[md5sum] = "0c8abc3dbfc29c2e90096e289d9d738b"
SRC_URI[sha256sum] = "4e42a091e6ae434d7c3146adb876a8a5dc29d9354560087a905a0bd7fb58d7a6"

PATH_prepend = "${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QT_BINS}:"

export USE_LIBPATH = "${libdir}"
export QUPZILLA_PREFIX = "${prefix}"
export SHARE_FOLDER = "${datadir}"
export QMAKE_LRELEASE = "${RECIPE_SYSROOT_NATIVE}/usr/bin/qt5"

FILES_${PN} += " \
    ${OE_QMAKE_PATH_DATA}/icons \
    ${OE_QMAKE_PATH_DATA}/metainfo \
    ${OE_QMAKE_PATH_DATA}/bash-completion/completions \
    ${OE_QMAKE_PATH_PLUGINS}/falkon \
"

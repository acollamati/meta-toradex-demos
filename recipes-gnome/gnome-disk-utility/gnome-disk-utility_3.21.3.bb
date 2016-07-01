SUMMARY = "GNOME disk utility"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "glib-2.0 gtk+3 libcanberra libdvdread libnotify libpwquality libsecret libunique udisks2 avahi-ui virtual/libx11 libatasmart gnome-doc-utils intltool-native libgnome-keyring"

PR = "r4"

inherit gnomebase gtk-icon-cache
SRC_URI[archive.md5sum] = "265ed1aede9ef0570228ca6908e9df59"
SRC_URI[archive.sha256sum] = "f39508226268f39dafa8977ebcec1d36e694f8aa7c4a404d1ac50f76e8e57739"
GNOME_COMPRESS_TYPE="xz"

#SRC_URI += "\
#    file://disable-scrollkeeper.patch \
#    file://fix-dbus-interfaces.patch \
#    file://sysrooted-pkg-config.patch \
#    file://0001-Add-support-for-DeviceAutomountHint.patch \
#    file://0002-Require-libnotify-0.6.1.patch \
#"

EXTRA_OECONF += "--disable-scrollkeeper --disable-gsd-plugin"

PACKAGECONFIG ??= ""
PACKAGECONFIG[nautilus] = "--enable-nautilus,--disable-nautilus,nautilus"

do_configure_prepend() {
    sed -i -e "s: help : :g" ${S}/Makefile.am
}

PACKAGES =+ "${PN}-nautilus-extension ${PN}-libs"
FILES_${PN}-nautilus-extension += "${libdir}/nautilus/extensions-2.0/*.so"
FILES_${PN}-libs += "${libdir}/libgdu*.so.*"
FILES_${PN}-dev += "${libdir}/nautilus/extensions-2.0/*.la"
FILES_${PN}-staticdev += "${libdir}/nautilus/extensions-2.0/*.a"
FILES_${PN}-dbg += "${libdir}/nautilus/extensions-2.0/.debug"

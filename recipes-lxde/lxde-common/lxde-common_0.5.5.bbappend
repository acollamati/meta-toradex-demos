PRINC = "10"
WALLPAPER-MACHINE = "Wallpaper_Toradex.png"
WALLPAPER-MACHINE_colibri-t20 = "Wallpaper_ColibriT20.png"
WALLPAPER-MACHINE_colibri-t30 = "Wallpaper_ColibriT30.png"
WALLPAPER-MACHINE_apalis-t30 = "Wallpaper_ApalisT30.png"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI += " \
    file://consistent_defconfig_dirs.patch \
    file://Wallpaper_Toradex.png \
    file://${WALLPAPER-MACHINE} \
    file://wallpaper.patch \
    file://desktop.conf \
    file://defaults.list \
    file://panel-buttons.patch \
"

# for colibri-vf50, colibri-vf61 we decide on the target during postinst
SRC_URI_append_vf += " \
    file://Wallpaper_ColibriVF50.png \
    file://Wallpaper_ColibriVF61.png \
"
# for apalis-imx6, we decide on the target during postinst
SRC_URI_append_mx6 += " \
    file://Wallpaper_ApalisiMX6D.png \
    file://Wallpaper_ApalisiMX6Q.png \
"

do_install_append () {
    install -m 0755 -d ${D}/${datadir}/lxde/wallpapers
    install -m 0644 ${WORKDIR}/Wallpaper*.png ${D}/${datadir}/lxde/wallpapers/
    ln -sf ${WALLPAPER-MACHINE} ${D}/${datadir}/lxde/wallpapers/toradex.png
    rm  ${D}/etc/xdg/lxsession/LXDE/desktop.conf
    install -m 0644 ${WORKDIR}/desktop.conf ${D}/etc/xdg/lxsession/LXDE/
    install -m 0755 -d ${D}/${datadir}/applications/
    install -m 0644 ${WORKDIR}/defaults.list ${D}/${datadir}/applications/
}

pkg_postinst_${PN}_vf () {
    # can't do this offline
    if [ "x$D" != "x" ]; then
        exit 1
    fi
    IS_VF50=`grep -c VF50 /proc/cpuinfo`
    if [ $IS_VF50 -gt 0 ]; then
        ln -sf Wallpaper_ColibriVF50.png ${datadir}/lxde/wallpapers/toradex.png
    else
        ln -sf Wallpaper_ColibriVF61.png ${datadir}/lxde/wallpapers/toradex.png
    fi
}

pkg_postinst_${PN}_mx6 () {
    # can't do this offline
    if [ "x$D" != "x" ]; then
        exit 1
    fi
    CORES=`grep -c processor /proc/cpuinfo`
    if [ $CORES -gt 2 ]; then
        ln -sf Wallpaper_ApalisiMX6Q.png ${datadir}/lxde/wallpapers/toradex.png
    else
        ln -sf Wallpaper_ApalisiMX6D.png ${datadir}/lxde/wallpapers/toradex.png
    fi
}

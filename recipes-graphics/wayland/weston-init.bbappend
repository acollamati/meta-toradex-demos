FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += "file://toradex-save-touchscreen-calibration.sh"

PACKAGECONFIG[touchscreen-calibration] = ",,"
PACKAGECONFIG:append:tdx = " touchscreen-calibration"

INI_UNCOMMENT_ASSIGNMENTS:append:tdx = " \
    \\[libinput\\] \
    enable_tap=true \
"

do_install:append:tdx() {
    if [ "${@bb.utils.contains('PACKAGECONFIG', 'touchscreen-calibration', 'yes', 'no', d)}" = "yes" ]; then
        sed -i -e "/^\[libinput\]/a calibration_helper=${bindir}/toradex-save-touchscreen-calibration" ${D}${sysconfdir}/xdg/weston/weston.ini
        sed -i -e "/^\[libinput\]/a touchscreen_calibrator=true" ${D}${sysconfdir}/xdg/weston/weston.ini
        install -Dm0755 ${WORKDIR}/toradex-save-touchscreen-calibration.sh ${D}${bindir}/toradex-save-touchscreen-calibration
    fi
}

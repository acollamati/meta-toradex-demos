FILESEXTRAPATHS_prepend := "${THISDIR}/base-files:"

SRC_URI_append_tegra3 = "file://fw_unlock_mmc.sh"

do_install_append () {
        echo "search colibri.net"  > ${D}${sysconfdir}/resolv.conf
        echo "nameserver 8.8.8.8" >> ${D}${sysconfdir}/resolv.conf
        echo "nameserver 8.8.4.4" >> ${D}${sysconfdir}/resolv.conf
}

do_install_append_tegra3() {
	install -d ${D}${sysconfdir}/profile.d/
	install -m 0644 ${WORKDIR}/fw_unlock_mmc.sh ${D}${sysconfdir}/profile.d/fw_unlock_mmc.sh
}

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_imxgpu = " file://0001-xeglgears-drop-usage-of-fooEXT-functions.patch"
PACKAGE_ARCH_imxgpu = "${MACHINE_SOCARCH}"

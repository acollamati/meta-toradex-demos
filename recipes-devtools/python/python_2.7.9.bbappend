FILESEXTRAPATHS_prepend := "${THISDIR}/python:"

SRC_URI_remove = "file://avoid_warning_about_tkinter.patch"

PACKAGECONFIG ?= ""

PACKAGECONFIG[tkinter] = ",,tk"
PACKAGECONFIG_remove_class-native = "tkinter"
PACKAGECONFIG_remove_class-nativesdk = "tkinter"
RDEPENDS_${PN}-tkinter_append += "${@base_contains('PACKAGECONFIG', 'tkinter', 'tcl tk', '', d)}"
SRC_URI += " \
  ${@base_contains('PACKAGECONFIG', 'tkinter', '', 'file://dont_build_tkinter.patch', d)} \
"
python __anonymous() {
    if not 'openembedded-layer' in d.getVar('BBFILE_COLLECTIONS'):
        if 'tkinter' in d.getVar('PACKAGECONFIG'):
            bb.error('Python PACKAGECONFIG tkinter requires tk provided by' \
                     'meta-oe layer but the layer is not available.')
}

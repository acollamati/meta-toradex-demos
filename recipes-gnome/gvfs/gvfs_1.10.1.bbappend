PACKAGECONFIG = "${@base_contains('INCOMPATIBLE_LICENSE', 'GPLv3', '', 'samba', d)}"
#do not build with smb/samba
PACKAGECONFIG_colibri-vf = ""
PACKAGE_ARCH_colibri-vf = "${MACHINE_ARCH}"
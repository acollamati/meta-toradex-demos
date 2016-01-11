#do not build with smb/samba
PACKAGECONFIG_remove = "samba"
DEPENDS += "udev"

#do not build with smb/samba
PACKAGECONFIG_remove = "samba"
#do not build with libgphoto2
PACKAGECONFIG_remove = "libgphoto2"

DEPENDS += "udev"

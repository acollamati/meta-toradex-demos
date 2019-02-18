#do not build with libgphoto2
PACKAGECONFIG_remove = "libgphoto2"

DEPENDS_append = " udev libusb1"

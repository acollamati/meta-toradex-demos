PACKAGECONFIG = "${@base_contains('DISTRO_FEATURES', 'x11', 'x11', 'wayland' ,d)}"

ARM_INSTRUCTION_SET_vf = "thumb"

PACKAGE_ARCH_vf = "${MACHINE_ARCH}"

PACKAGECONFIG_GL_tegra = "gles2"
PACKAGECONFIG_EXAMPLES ?= "examples"

# | /build/krm/oe-core_V2.6/build/out-glibc/work/armv7at2hf-vfp-neon-mx6qdl-angstrom-linux-gnueabi/qtbase/5.5.1+gitAUTOINC+5afc431323-r0/git/src/widgets/styles/qgtkstyle.cpp: In member function 'virtual QRect QGtkStyle::subControlRect(QStyle::ComplexControl, const QStyleOptionComplex*, QStyle::SubControl, const QWidget*) const':
# | /build/krm/oe-core_V2.6/build/out-glibc/work/armv7at2hf-vfp-neon-mx6qdl-angstrom-linux-gnueabi/qtbase/5.5.1+gitAUTOINC+5afc431323-r0/git/src/widgets/styles/qgtkstyle.cpp:3636:24: error: 'isInstanceOf' is not a member of 'QStyleHelper'
# |              } else if (QStyleHelper::isInstanceOf(groupBox->styleObject, QAccessible::Grouping)) {
# |                         ^
# | /build/krm/oe-core_V2.6/build/out-glibc/work/armv7at2hf-vfp-neon-mx6qdl-angstrom-linux-gnueabi/qtbase/5.5.1+gitAUTOINC+5afc431323-r0/git/src/widgets/styles/qgtkstyle.cpp:3636:74: error: 'QAccessible' has not been declared
# |              } else if (QStyleHelper::isInstanceOf(groupBox->styleObject, QAccessible::Grouping)) {

PACKAGECONFIG_append = " accessibility"
PACKAGECONFIG_append = " sql-sqlite"

PACKAGECONFIG_FONTS_append = " fontconfig"

#qtbase must be configured with icu for qtwebkit
PACKAGECONFIG_append = " \
    icu \
    ${PACKAGECONFIG_EXAMPLES} \
"

PACKAGE_ARCH_tegra = "${MACHINE_ARCH}"

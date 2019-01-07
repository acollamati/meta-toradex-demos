#create the deployment directory-tree

include conf/tdx_version.conf

PV = "${TDX_VER_PACKAGE_MIN}"
IMAGE_DIR = "${IMAGE_NAME}_${TDX_VER_ITEM}"
IMAGE_ROOTFS = "${WORKDIR}/${IMAGE_DIR}/rootfs"

UBOOT_EXT = "bin"
UBOOT_EXT_mx6 = "img"
UBOOT_EXT_use-mainline-bsp = "img"
UBOOT_EXT_colibri-imx6ull = "imx"
UBOOT_EXT_mx7 = "imx"
UBOOT_EXT_vf = "imx"
UBOOT_SYMLINK = "u-boot-${MACHINE}.${UBOOT_EXT}"
UBOOT_SYMLINK_tegra124 = "u-boot-dtb-tegra-${MACHINE}.${UBOOT_EXT}"
UBOOT_SYMLINK_tegra124m = "u-boot-dtb-tegra-${MACHINE}.${UBOOT_EXT}"
UBOOT_BINARY = "u-boot.${UBOOT_EXT}"
UBOOT_BINARY_colibri-imx7 = "u-boot-nand.${UBOOT_EXT}"
UBOOT_BINARY_colibri-imx6ull = "u-boot-nand.${UBOOT_EXT}"
UBOOT_BINARY_tegra124 = "u-boot-dtb-tegra.${UBOOT_EXT}"
UBOOT_BINARY_tegra124m = "u-boot-dtb-tegra.${UBOOT_EXT}"

#we need some native tools for deployment
DEPENDS_append = " pbzip2-native tdx-updater"
DEPENDS_append_mx6 = " imx-loader-native"
DEPENDS_append_use-mainline-bsp = " imx-loader-native"
DEPENDS_append_mx6ull = " mtd-utils-native imx-loader-native"
DEPENDS_append_mx7 = " mtd-utils-native imx-loader-native"
DEPENDS_append_tegra124 = " cbootimage-native tegrarcm-native"
DEPENDS_append_tegra124m = " cbootimage-native tegrarcm-native"
DEPENDS_append_vf = " mtd-utils-native imx-loader-native"
#we need mkimage to produce the U-Boot scripts even if not building a uImage kernel
DEPENDS_append_arm = " virtual/bootloader u-boot-mkimage-native"
DEPENDS_remove_qemuall = "virtual/bootloader"

imagedeploytools() {
    # default to nothing to deploy
    :
}

imagedeploytools-all-mx6qdl() {
    IMAGE_ROOT="${IMAGE_ROOTFS}/../imx_flash/"
    rm -f ${IMAGE_ROOT}/imx_flash/imx_usb ${IMAGE_ROOT}/imx_flash/*.conf
    mkdir -p ${IMAGE_ROOT}

    # add imx-loader and its configuration files
    cp ${STAGING_BINDIR_NATIVE}/imx_usb ${IMAGE_ROOT}/
    ${BUILD_STRIP} ${IMAGE_ROOT}/imx_usb
    cp ${STAGING_ETCDIR_NATIVE}/imx-loader.d/imx_usb.conf ${IMAGE_ROOT}/
    cp ${STAGING_ETCDIR_NATIVE}/imx-loader.d/mx6_usb_*.conf ${IMAGE_ROOT}/
}

imagedeploytools_append_mx6() {
    IMAGE_ROOT="${IMAGE_ROOTFS}/../imx_flash/"
    rm -f ${IMAGE_ROOT}/imx_flash/imx_usb ${IMAGE_ROOT}/imx_flash/*.conf
    mkdir -p ${IMAGE_ROOT}

    # add imx-loader and its configuration files
    cp ${STAGING_BINDIR_NATIVE}/imx_usb ${IMAGE_ROOT}/
    ${BUILD_STRIP} ${IMAGE_ROOT}/imx_usb
    cp ${STAGING_ETCDIR_NATIVE}/imx-loader.d/imx_usb.conf ${IMAGE_ROOT}/
    cp ${STAGING_ETCDIR_NATIVE}/imx-loader.d/mx6_usb_*.conf ${IMAGE_ROOT}/
}

imagedeploytools_append_use-mainline-bsp() {
    IMAGE_ROOT="${IMAGE_ROOTFS}/../imx_flash/"
    rm -f ${IMAGE_ROOT}/imx_flash/imx_usb ${IMAGE_ROOT}/imx_flash/*.conf
    mkdir -p ${IMAGE_ROOT}

    # add imx-loader and its configuration files
    cp ${STAGING_BINDIR_NATIVE}/imx_usb ${IMAGE_ROOT}/
    ${BUILD_STRIP} ${IMAGE_ROOT}/imx_usb
    cp ${STAGING_ETCDIR_NATIVE}/imx-loader.d/imx_usb.conf ${IMAGE_ROOT}/
    cp ${STAGING_ETCDIR_NATIVE}/imx-loader.d/mx6_usb_*.conf ${IMAGE_ROOT}/

}

imagedeploytools_append_mx6ull() {
    IMAGE_ROOT="${IMAGE_ROOTFS}/../imx_flash/"
    rm -f ${IMAGE_ROOT}/mkfs.ubifs  ${IMAGE_ROOT}/imx_usb
    mkdir -p ${IMAGE_ROOT}

    # add the mkfs.ubifs binary
    cp ${STAGING_DIR_NATIVE}/usr/sbin/mkfs.ubifs ${IMAGE_ROOT}/
    ${BUILD_STRIP} ${IMAGE_ROOT}/mkfs.ubifs

    # add imx-loader and its configuration files
    cp ${STAGING_BINDIR_NATIVE}/imx_usb ${IMAGE_ROOT}/
    ${BUILD_STRIP} ${IMAGE_ROOT}/imx_usb

# FIXME
    cp ${STAGING_ETCDIR_NATIVE}/imx-loader.d/imx_usb.conf ${IMAGE_ROOT}/
    cp ${STAGING_ETCDIR_NATIVE}/imx-loader.d/mx7_usb_*.conf ${IMAGE_ROOT}/
}

imagedeploytools_append_mx7() {
    IMAGE_ROOT="${IMAGE_ROOTFS}/../imx_flash/"
    rm -f ${IMAGE_ROOT}/mkfs.ubifs  ${IMAGE_ROOT}/imx_usb
    mkdir -p ${IMAGE_ROOT}

    # add the mkfs.ubifs binary
    cp ${STAGING_DIR_NATIVE}/usr/sbin/mkfs.ubifs ${IMAGE_ROOT}/
    ${BUILD_STRIP} ${IMAGE_ROOT}/mkfs.ubifs

    # add imx-loader and its configuration files
    cp ${STAGING_BINDIR_NATIVE}/imx_usb ${IMAGE_ROOT}/
    ${BUILD_STRIP} ${IMAGE_ROOT}/imx_usb
    cp ${STAGING_ETCDIR_NATIVE}/imx-loader.d/imx_usb.conf ${IMAGE_ROOT}/
    cp ${STAGING_ETCDIR_NATIVE}/imx-loader.d/mx7_usb_*.conf ${IMAGE_ROOT}/
    cp ${MACHINE_BIN}/${UBOOT_BINARY} ${MACHINE_BIN}/u-boot.imx
}

imagedeploytools_append_tegra124() {
    IMAGE_ROOT="${IMAGE_ROOTFS}/../tegra-uboot-flasher/"
    mkdir -p ${IMAGE_ROOT}

    # add cbootimage and tegrarcm
    cp ${STAGING_BINDIR_NATIVE}/cbootimage ${IMAGE_ROOT}/
    cp ${STAGING_BINDIR_NATIVE}/tegrarcm ${IMAGE_ROOT}/
    ${BUILD_STRIP} ${IMAGE_ROOT}/cbootimage
    ${BUILD_STRIP} ${IMAGE_ROOT}/tegrarcm
}

imagedeploytools_append_tegra124m() {
    IMAGE_ROOT="${IMAGE_ROOTFS}/../tegra-uboot-flasher/"
    mkdir -p ${IMAGE_ROOT}

    # add cbootimage and tegrarcm
    cp ${STAGING_BINDIR_NATIVE}/cbootimage ${IMAGE_ROOT}/
    cp ${STAGING_BINDIR_NATIVE}/tegrarcm ${IMAGE_ROOT}/
    ${BUILD_STRIP} ${IMAGE_ROOT}/cbootimage
    ${BUILD_STRIP} ${IMAGE_ROOT}/tegrarcm
}


imagedeploytools_append_vf() {
    IMAGE_ROOT="${IMAGE_ROOTFS}/../vf_flash/"
    rm -f ${IMAGE_ROOT}/mkfs.ubifs  ${IMAGE_ROOT}/imx_uart
    mkdir -p ${IMAGE_ROOT}

    # add the mkfs.ubifs binary
    cp ${STAGING_DIR_NATIVE}/usr/sbin/mkfs.ubifs ${IMAGE_ROOT}/
    ${BUILD_STRIP} ${IMAGE_ROOT}/mkfs.ubifs

    # add imx-loader and its configuration files
    cp ${STAGING_BINDIR_NATIVE}/imx_uart ${IMAGE_ROOT}/
    ${BUILD_STRIP} ${IMAGE_ROOT}/imx_uart*
    cp ${STAGING_ETCDIR_NATIVE}/imx-loader.d/vybrid_usb_*.conf ${IMAGE_ROOT}/
}

imagedeploy_kernel() {
    # put kernel, existing device-tree(s) into the bin directories
    cp -pP ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}* ${MACHINE_BIN}/
    ls ${DEPLOY_DIR_IMAGE}/*.dtb 2> /dev/null && cp -pP ${DEPLOY_DIR_IMAGE}/*.dtb ${MACHINE_BIN}/
    mv ${MACHINE_BIN}/${KERNEL_IMAGETYPE}-${MACHINE}.bin ${MACHINE_BIN}/${KERNEL_IMAGETYPE}
}

fakeroot imagedeploy() {
    :
}

fakeroot imagedeploy_arm() {
    cd ${WORKDIR}
    cp -Lr ${RECIPE_SYSROOT}${datadir}/update/* ${IMAGE_DIR}/
    export MACHINE_BIN=$(readlink -e ${IMAGE_ROOTFS}/../*_bin)

    sed -i 's/%kernel_device_trees%/${KERNEL_DEVICETREE}/' ${IMAGE_DIR}/update.sh

    cd ${IMAGE_DIR}/*_bin
    ./mk-u-boot-scripts.sh
    cd ${WORKDIR}

    imagedeploy_kernel

    cp -P ${DEPLOY_DIR_IMAGE}/u-boot* ${MACHINE_BIN}/
    if [ -e ${DEPLOY_DIR_IMAGE}/SPL ]
    then
        cp -P ${DEPLOY_DIR_IMAGE}/SPL* ${MACHINE_BIN}/
    fi
    rm -f ${MACHINE_BIN}/u-boot-hsmmc-${MACHINE}.${UBOOT_EXT}
    mv ${MACHINE_BIN}/${UBOOT_SYMLINK} ${MACHINE_BIN}/${UBOOT_BINARY}

    # add additional tools if required
    imagedeploytools

    # remove intermediate file rootfs.lock
    rm -f ${IMAGE_DIR}/rootfs.lock

    #create tarball
    # ignore return code 1 "file changed as we read it" as other tasks(e.g. do_image_wic) may be hardlinking rootfs
    tar --use-compress-prog=pbzip2 --numeric-owner -cf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}_${TDX_VER_PACKAGE}.tar.bz2 ${IMAGE_DIR} || true
}

fakeroot do_imagedeploy() {
    if [ ! -e ${RECIPE_SYSROOT}/${datadir}/update/no_update_defined.txt ]
    then
        imagedeploy
    fi
}
addtask imagedeploy after do_image before do_image_complete

# make sure to rerun do_rootfs before do_imagedeploy is executed
do_imagedeploy[vardep] = "do_rootfs"

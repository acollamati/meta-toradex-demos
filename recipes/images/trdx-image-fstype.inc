#create the deployment directory-tree

BURNFLASH := "${THISDIR}/files/${MACHINE}/burnflash.tar.bz2"
IMAGE_ROOTFS = "${WORKDIR}/${IMAGE_NAME}${PV}/rootfs"

U_BOOT_EXT = "bin"
U_BOOT_EXT_vf = "imx"
U_BOOT_EXT_mx6 = "imx"

#we need some native tools for deployment
DEPENDS_append_colibri-vf = " mtd-utils-native imx-loader-native"
DEPENDS_append_mx6 = " imx-loader-native"

imagedeploytools() {
    # default to nothing to deploy
    :
}
imagedeploytools_colibri-vf() {
    IMAGE_ROOT="${IMAGE_ROOTFS}/../vf_flash/"
    rm -f ${IMAGE_ROOT}/mkfs.ubifs  ${IMAGE_ROOT}/imx_uart
    mkdir -p ${IMAGE_ROOT}

    # add the mkfs.ubifs binary
    cp ${STAGING_DIR_NATIVE}/usr/sbin/mkfs.ubifs ${IMAGE_ROOT}/
    ${BUILD_STRIP} ${IMAGE_ROOT}/mkfs.ubifs

    # add imx-loader and its configuration files
    cp ${STAGING_BINDIR_NATIVE}/imx_uart ${IMAGE_ROOT}/
    ${BUILD_STRIP} ${IMAGE_ROOT}/imx_uart*
    cp ${STAGING_ETCDIR_NATIVE}/imx-loader.d/* ${IMAGE_ROOT}/
}

imagedeploytools_append_mx6() {
    IMAGE_ROOT="${IMAGE_ROOTFS}/../imx_flash/"
    rm -f ${IMAGE_ROOT}/imx_flash/imx_usb ${IMAGE_ROOT}/imx_flash/*.conf
    mkdir -p ${IMAGE_ROOT}

    # add imx-loader and its configuration files
    cp ${STAGING_BINDIR_NATIVE}/imx_usb ${IMAGE_ROOT}/
    ${BUILD_STRIP} ${IMAGE_ROOT}/imx_usb ${IMAGE_ROOT}/imx_usb.m32
    cp ${STAGING_ETCDIR_NATIVE}/imx-loader.d/* ${IMAGE_ROOT}/
}

fakeroot do_imagedeploy() {
    cd ${WORKDIR}
    tar -xf ${BURNFLASH}
    cp -pPr burnflash/* ${IMAGE_NAME}${PV}/

    # put u-boot, kernel into the bin directories, remove the kernel/dtb from the rootfs/boot
    rm -f ${IMAGE_ROOTFS}/boot/uImage* ${IMAGE_ROOTFS}/boot/*.dtb
    cp -pP ${DEPLOY_DIR_IMAGE}/uImage* ${IMAGE_ROOTFS}/../${MACHINE}_bin/
    ls ${DEPLOY_DIR_IMAGE}/*.dtb 2> /dev/null && cp -pP ${DEPLOY_DIR_IMAGE}/*.dtb ${IMAGE_ROOTFS}/../${MACHINE}_bin/
    mv ${IMAGE_ROOTFS}/../${MACHINE}_bin/uImage-${MACHINE}.bin ${IMAGE_ROOTFS}/../${MACHINE}_bin/uImage

    cp -pP ${DEPLOY_DIR_IMAGE}/u-boot* ${IMAGE_ROOTFS}/../${MACHINE}_bin/
    rm -f ${IMAGE_ROOTFS}/../${MACHINE}_bin/u-boot-hsmmc-${MACHINE}.${U_BOOT_EXT}
    mv ${IMAGE_ROOTFS}/../${MACHINE}_bin/u-boot-${MACHINE}.${U_BOOT_EXT} ${IMAGE_ROOTFS}/../${MACHINE}_bin/u-boot.${U_BOOT_EXT}

    # add the rootfs version to the welcome banner
    echo "${IMAGE_NAME}${PV}_${DATE}" >> ${IMAGE_ROOTFS}/etc/issue
    echo "" >> ${IMAGE_ROOTFS}/etc/issue

    # add additional tools if required
    imagedeploytools

    # remove intermediate file rootfs.lock
    rm -f ${IMAGE_NAME}${PV}/rootfs.lock

    #create tarball
    DATE=`date +%Y%m%d`
    tar -cjf ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${PV}_${DATE}.tar.bz2 ${IMAGE_NAME}${PV}
}

addtask imagedeploy after do_rootfs before do_rm_work

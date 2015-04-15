#!/bin/sh
# Prepare files needed for flashing a Apalis/Colibri T30 module
#
# inspired by meta-fsl-arm/classes/image_types_fsl.bbclass

# sometimes we need the binary echo, not the shell builtin
ECHO=`which echo`
#some distros have fs tools only in root's path
PARTED=`which parted` 2> /dev/null
if [ -e "$PARTED" ] ; then
	MKFSVFAT=`which mkfs.vfat`
else
	PARTED=`sudo which parted`
	MKFSVFAT=`sudo which mkfs.vfat`
fi

Flash()
{
	echo ""
	echo "To flash the Apalis/Colibri T30 module, boot the module to the U-Boot prompt and"
	echo ""
	echo "when using a SD-card, insert the SD-card and enter:"
	echo "run setupdate"
	echo ""
	echo "when using tftp, connect Ethernet and enter:"
	echo "tftp \$loadaddr flash_eth.img ; source \$loadaddr"
	echo ""
	echo "then enter to update all:"
	echo "run update"
	echo ""
	echo "to update a single component enter one of:"
	echo "run update_uboot"
	echo "run update_kernel"
	echo ""
	echo ""
	echo "If you don't have a working U-Boot anymore, connect your PC to the module's"
	echo "USB client port, bring the module in the serial download mode and start"
	echo "the update.sh script with the -d option. This will copy U-Boot into the"
	echo "modules RAM and execute it."
}

Usage()
{
	echo ""
	echo "Prepares and copies files for flashing the internal eMMC of a Apalis/Colibri T30"
	echo ""
	echo "Will require a running U-Boot on the target. Either one already flashed"
	echo "on the eMMC or one copied over usb into the module's RAM"
	echo ""
	echo "-d           : use USB connection to copy and execute U-Boot to the module's RAM"
	echo "-h           : Prints this message"
	echo "-o directory : output directory, defaults to /media/KERNEL"
	echo "               if this is on a SD card, it should be the"
	echo "               first partition formated with VFAT"
	echo ""
	echo "Example \"./update.sh\" copies the requiered files to /media/KERNEL/"
	echo "Example \"./update.sh -o /srv/tftp/\" copies the requiered files to /srv/tftp/"
	echo ""
	echo ""
	Flash
}

#initialise options
UBOOT_RECOVERY=0
OUT_DIR="/media/KERNEL"
MIN_PARTITION_FREE_SIZE=100
ROOTFSPATH=rootfs
# No devicetree by default
KERNEL_DEVICETREE=""
KERNEL_IMAGETYPE="uImage"

while getopts "dho:" Option ; do
	case $Option in
		d)	UBOOT_RECOVERY=1
			;;
		h) 	Usage
			# Exit if only usage (-h) was specfied.
			if [ "$#" -eq 1 ] ; then
				exit 10
			fi
			exit 0
			;;
		o)	OUT_DIR=$OPTARG
			;;
	esac
done

# autotect MODTYPE from from rootfs directory
CNT=`grep -ic "t30" rootfs/etc/issue || true`
if [ "$CNT" -ge 1 ] ; then
	CNT=`grep -ic "apalis" rootfs/etc/issue || true`
	if [ "$CNT" -ge 1 ] ; then
		echo "Apalis T30 rootfs detected"
		MODTYPE=apalis-t30
		IMAGEFILE=root.ext3
		U_BOOT_BINARY=u-boot-dtb-tegra.bin
		KERNEL_DEVICETREE="tegra30-apalis-eval.dtb"
		LOCPATH="tegra-uboot-flasher"
		# eMMC size [in sectors of 512]
		EMMC_SIZE=$(expr 1024 \* 3500 \* 2)
		BCT=apalis_t30_12MHz_MT41K512M8RH-125_533MHz.bct
		CBOOT_IMAGE=apalis_t30.img
	else
		echo "Colibri T30 rootfs detected"
		MODTYPE=colibri-t30
		IMAGEFILE=root.ext3
		U_BOOT_BINARY=u-boot-dtb-tegra.bin
		KERNEL_DEVICETREE="tegra30-colibri-eval-v3.dtb"
		LOCPATH="tegra-uboot-flasher"
		EMMC_SIZE=$(expr 1024 \* 2000 \* 2)
		# with new kernel, boot with 400MHz, then switch between 400 & 800
		BCT=colibri_t30_12MHz_NT5CC256M16CP-DI_400MHz.bct
#		BCT=colibri_t30_12MHz_NT5CC256M16CP-DI_533MHz.bct
		CBOOT_IMAGE=colibri_t30.img
	fi
else
	echo "can not detect module type from ./rootfs/etc/issue"
	echo "exiting"
	exit 1
fi
BINARIES=${MODTYPE}_bin

#is only U-Boot to be copied to RAM?
if [ "$UBOOT_RECOVERY" -ge 1 ] ; then
	cd ${LOCPATH}
	sudo ./tegrarcm --bct=../${BINARIES}/${BCT} --bootloader=../${BINARIES}/${U_BOOT_BINARY} --loadaddr=0x80108000
	exit 1
fi

# is OUT_DIR an existing directory?
if [ ! -d "$OUT_DIR" ] ; then
	echo "$OUT_DIR" "does not exist, exiting"
	exit 1
fi

#sanity check for correct untared rootfs
DEV_OWNER=`ls -ld rootfs/dev | awk '{print $3}'`
if [ "${DEV_OWNER}x" != "rootx" ]
then
	$ECHO -e "rootfs/dev is not owned by root, but it should!"
	$ECHO -e "\033[1mPlease unpack the tarball with root rights.\033[0m"
	$ECHO -e "e.g. sudo tar xjvf Apalis_T30_LinuxImageV2.1_20140201.tar.bz2"
	exit 1
fi

#sanity check for existence of U-Boot and kernel
[ -e ${BINARIES}/${U_BOOT_BINARY} ] || { echo "${BINARIES}/${U_BOOT_BINARY} does not exist"; exit 1; }
[ -e ${BINARIES}/uImage ] || { echo "${BINARIES}/uImage does not exist"; exit 1; }

#sanity check for some programs
MCOPY=`sudo which mcopy`
[ "${MCOPY}x" != "x" ] || { echo >&2 "Program mcopy not available.  Aborting."; exit 1; }
sudo ${PARTED} -v >/dev/null 2>&1 || { echo >&2 "Program parted not available.  Aborting."; exit 1; }
[ "${MKFSVFAT}x" != "x" ] || { echo >&2 "Program mkfs.vfat not available.  Aborting."; exit 1; }
MKFSEXT3=`sudo which mkfs.ext3`
[ "${MKFSEXT3}x" != "x" ] || { echo >&2 "Program mkfs.ext3 not available.  Aborting."; exit 1; }
awk -V  >/dev/null 2>&1 || { echo >&2 "Program awk not available.  Aborting."; exit 1; }
dd --help >/dev/null 2>&1 || { echo >&2 "Program dd not available.  Aborting."; exit 1; }
tegra-uboot-flasher/cbootimage -h >/dev/null 2>&1
[ "$?" -lt 2 ] || { echo >&2 "Program cbootimage not available. 32bit compatibility libs?  Aborting."; exit 1; }

#make the directory with the outputfiles writable
sudo chown $USER: ${BINARIES}

#make a file with the used versions for U-Boot, kernel and rootfs
rm ${BINARIES}/versions.txt
touch ${BINARIES}/versions.txt
echo "Component Versions" > ${BINARIES}/versions.txt
basename "`readlink -e ${BINARIES}/${U_BOOT_BINARY}`" >> ${BINARIES}/versions.txt
basename "`readlink -e ${BINARIES}/uImage`" >> ${BINARIES}/versions.txt
$ECHO -n "Rootfs " >> ${BINARIES}/versions.txt
grep -i t30 rootfs/etc/issue >> ${BINARIES}/versions.txt


# The emmc layout used is:
#
# boot area partition 1 aka eMMC boot sector:
#
# with cbootimage containing BCT and U-Boot boot loader
#
# user area aka general purpose eMMC region:
#
#    0                      -> IMAGE_ROOTFS_ALIGNMENT         - reserved to bootloader (not partitioned)
#    IMAGE_ROOTFS_ALIGNMENT -> BOOT_SPACE                     - kernel and other data
#    BOOT_SPACE             -> SDIMG_SIZE                     - rootfs
#
#                                                     Default Free space = 1.3x
#                                                     Use IMAGE_OVERHEAD_FACTOR to add more space
#                                                     <--------->
#            4MiB               16MiB           SDIMG_ROOTFS                    4MiB
# <-----------------------> <----------> <----------------------> <------------------------------>
#  ------------------------ ------------ ------------------------ -------------------------------
# | IMAGE_ROOTFS_ALIGNMENT | BOOT_SPACE | ROOTFS_SIZE            |     IMAGE_ROOTFS_ALIGNMENT    |
#  ------------------------ ------------ ------------------------ -------------------------------
# ^                        ^            ^                        ^                               ^
# |                        |            |                        |                               |
# 0                      4096     4MiB +  16MiB       4MiB +  16Mib + SDIMG_ROOTFS   4MiB +  16MiB + SDIMG_ROOTFS + 4MiB
#
# with the U-Boot environment at 512 * 1024, the config block is at 640 * 1024


# generate cbootimage containing BCT and U-Boot boot loader
cd ${BINARIES}
sudo ../${LOCPATH}/cbootimage -s tegra30 ${CBOOT_IMAGE}.cfg ${CBOOT_IMAGE}
cd ..


# Boot partition [in sectors of 512]
BOOT_START=$(expr 4096 \* 2)
# Rootfs partition [in sectors of 512]
ROOTFS_START=$(expr 20480 \* 2)
# Boot partition volume id
BOOTDD_VOLUME_ID="Boot"

#make the partition size size(rootfs used + MIN_PARTITION_FREE_SIZE)
#add about 4% to the rootfs to account for fs overhead. (/1024/985 instead of /1024/1024).                                                                                    
#add 512 bytes per file to account for small files
NUMBER_OF_FILES=`sudo find ${ROOTFSPATH} | wc -l`
EXT_SIZE=`sudo du -DsB1 ${ROOTFSPATH} | awk -v min=$MIN_PARTITION_FREE_SIZE -v f=${NUMBER_OF_FILES} \
		'{rootfs_size=$1+f*512;rootfs_size=int(rootfs_size/1024/985); print (rootfs_size+min) }'`

echo ""
echo "Creating MBR file and do the partitioning"
# Initialize a sparse file
dd if=/dev/zero of=${BINARIES}/mbr.bin bs=512 count=0 seek=${EMMC_SIZE}
${PARTED} -s ${BINARIES}/mbr.bin mklabel msdos
${PARTED} -a none -s ${BINARIES}/mbr.bin unit s mkpart primary fat32 ${BOOT_START} $(expr ${ROOTFS_START} - 1 )
# the partition spans to the end of the disk, even though the fs size will be smaller
# on the target the fs is then grown to the full size
${PARTED} -a none -s ${BINARIES}/mbr.bin unit s mkpart primary ext2 ${ROOTFS_START} $(expr ${EMMC_SIZE} \- ${ROOTFS_START} \- 1)
${PARTED} -s ${BINARIES}/mbr.bin unit s print 
# get the size of the VFAT partition
BOOT_BLOCKS=$(LC_ALL=C ${PARTED} -s ${BINARIES}/mbr.bin unit b print \
	| awk '/ 1 / { print int(substr($4, 1, length($4 -1)) / 1024) }')
# now crop the file to only the MBR size
IMG_SIZE=512
truncate -s $IMG_SIZE ${BINARIES}/mbr.bin


echo ""
echo "Creating VFAT partion image with the kernel"
rm -f ${BINARIES}/boot.vfat
${MKFSVFAT} -n "${BOOTDD_VOLUME_ID}" -S 512 -C ${BINARIES}/boot.vfat $BOOT_BLOCKS 
export MTOOLS_SKIP_CHECK=1
mcopy -i ${BINARIES}/boot.vfat -s ${BINARIES}/uImage ::/uImage

# Copy device tree file
if test -n "${KERNEL_DEVICETREE}"; then
	for DTS_FILE in ${KERNEL_DEVICETREE}; do
		DTS_BASE_NAME=`basename ${DTS_FILE} | awk -F "." '{print $1}'`
		if [ -e "${BINARIES}/${KERNEL_IMAGETYPE}-${DTS_BASE_NAME}.dtb" ]; then
			kernel_bin="`readlink ${BINARIES}/${KERNEL_IMAGETYPE}`"
			kernel_bin_for_dtb="`readlink ${BINARIES}/${KERNEL_IMAGETYPE}-${DTS_BASE_NAME}.dtb | sed "s,$DTS_BASE_NAME,${MODTYPE},g;s,\.dtb$,.bin,g"`"
			if [ "$kernel_bin" = "$kernel_bin_for_dtb" ]; then
				mcopy -i ${BINARIES}/boot.vfat -s ${BINARIES}/${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${DTS_BASE_NAME}.dtb ::/${DTS_BASE_NAME}.dtb
				#copy also to out_dir
				sudo cp ${BINARIES}/${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${DTS_BASE_NAME}.dtb "$OUT_DIR/${DTS_BASE_NAME}.dtb"
			fi
		fi
	done
fi

echo ""
echo "Creating rootfs partion image"
rm -f ${BINARIES}/${IMAGEFILE}
sudo $LOCPATH/genext3fs.sh -d rootfs -b ${EXT_SIZE} ${BINARIES}/${IMAGEFILE} || exit 1


#copy to $OUT_DIR
sudo cp ${BINARIES}/configblock.bin ${BINARIES}/${CBOOT_IMAGE} ${BINARIES}/uImage ${BINARIES}/mbr.bin ${BINARIES}/boot.vfat ${BINARIES}/${IMAGEFILE} ${BINARIES}/flash*.img ${BINARIES}/versions.txt "$OUT_DIR"
sync

Flash

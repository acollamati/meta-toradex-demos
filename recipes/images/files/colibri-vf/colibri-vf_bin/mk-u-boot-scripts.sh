#!/bin/sh
for f in *.scr
do
	mkimage -T script -C none -n 'Flash Colibri VF' -d $f `basename $f .scr`.img
done	
#mkimage -T script -C none -n 'Flash Colibri VF with data from SD card' -d flash_mmc.scr flash_mmc.img
#mkimage -T script -C none -n 'Flash Colibri VF with u-boot from SD card' -d flash_mmc_u-boot.scr flash_mmc_u-boot.img

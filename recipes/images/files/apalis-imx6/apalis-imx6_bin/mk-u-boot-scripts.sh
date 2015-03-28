#!/bin/sh
for f in *.scr
do
	mkimage -T script -C none -n 'Flash Apalis iMX6' -d $f `basename $f .scr`.img
done	

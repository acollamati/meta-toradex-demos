# use this patch: http://cgit.openembedded.org/meta-openembedded/commit/?h=master-next&id=bf8f27af3954ab68e39712516ab515de90b4db27

FILESEXTRAPATHS_prepend := "${THISDIR}/gstreamer:"
SRC_URI = "http://gstreamer.freedesktop.org/src/gstreamer/gstreamer-${PV}.tar.bz2 \
           file://check_fix.patch \
           file://gst-inspect-check-error.patch \
           file://0001-baseparse-Fix-self-comparison-always-evaluates-to-tr.patch \
           file://0001-parse-make-grammar.y-work-with-Bison-3.patch \
"

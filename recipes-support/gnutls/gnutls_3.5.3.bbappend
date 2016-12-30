FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

# Backport commits in 3.5.6
# https://bugzilla.redhat.com/show_bug.cgi?id=1387141
SRC_URI_append = "file://0001-_gnutls_rnd_check-call-_rnd_system_entropy_check-dir.patch \
                  file://0002-rng-split-initialization-in-preinit-and-init.patch \
                  file://0003-deprecated-_gnutls_rnd-in-favor-of-exported-gnutls_r.patch \
"
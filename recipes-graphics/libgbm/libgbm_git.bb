SUMMARY = "generic buffer management API"
HOMEPAGE = "https://github.com/mozilla/gecko-dev/tree/master/third_party/gbm"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "\
    file://LICENSE;md5=d643a4da58f23a8e3f046871d2ddcea3 \
"

SRC_URI += "git://github.com/I-asked/gbm.git;protocol=https;branch=main"

SRCREV = "${AUTOREV}"
PR = "r1"
PV = "+git${SRCPV}"
S = "${WORKDIR}/git"

do_install () {
    install -d ${D}${includedir}
    install -m 644 ${S}/gbm.h ${D}${includedir}
}

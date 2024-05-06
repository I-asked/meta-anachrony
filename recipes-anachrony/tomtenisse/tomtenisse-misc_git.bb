SUMMARY = "A replacement suite for Gonk for running B2G on top of libhybris"
HOMEPAGE = "https://github.com/I-asked/tomtenisse-misc"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=107782241a20501d00a4f410c768c4cd"

SRC_URI = "git://github.com/I-asked/tomtenisse-misc.git;protocol=https;branch=main"
SRCREV = "${AUTOREV}"
PR = "r1"
PV = "+git${SRCPV}"
S = "${WORKDIR}/git"

RDEPENDS:${PN} += " b2g mir mir-android-platform"

FILES:${PN} += "/usr/lib/systemd/user/ /usr/lib/systemd/user/default.target.wants/"

do_install:append () {
    export DESTDIR=${D}/usr
    oe_runmake install
}

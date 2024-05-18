SUMMARY = "custom DRI3-based Xorg extension, which allows sharing Android gralloc-allocated buffers through libhybris"

HOMEPAGE = "https://gitlab.com/ubports/development/core/hybris-support/drihybris"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=09f012d5887ae0e2db5d865786502008"

SRC_URI += "git://gitlab.com/ubports/development/core/hybris-support/drihybris.git;protocol=https;branch=main"

DEPENDS += "xserver-xorg libxi"

SRCREV = "${AUTOREV}"
PR = "r1"
PV = "+git${SRCPV}"
S = "${WORKDIR}/git"

inherit autotools pkgconfig

FILES:${PN} += "${libdir}/xorg/modules"

do_install:append () {
    ln -fs xorg/modules/libdrihybris.so ${D}${libdir}/libdrihybris.so
}

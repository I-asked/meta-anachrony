SUMMARY = "generic buffer management API"
HOMEPAGE = "https://github.com/caramelli/yagears"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "\
    file://COPYING;md5=6a5d75139b6c3a5ac962aa7f43fd6515 \
"

DEPENDS = " virtual/libgles1 virtual/libgles2 libxcb xorgproto xtrans pixman libxkbfile libxfont2 libdrm libxcvt libxshmfence libxext wayland wayland-protocols drihybris vim-native "

SRC_URI += "git://github.com/caramelli/yagears.git;protocol=https;branch=master \
            file://0001-Make-yagears-more-similar-to-glxtest.patch \
           "

SRCREV = "656424b6f94e2a74564f91390b28ad32879221ec"
PR = "r1"
PV = "+git${SRCPV}"
S = "${WORKDIR}/git"

inherit autotools pkgconfig

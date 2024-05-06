SUMMARY = "XCursor Transparent Theme"
HOMEPAGE = "http://downloads.yoctoproject.org/releases/matchbox/utils/xcursor-transparent-readme.txt"
SECTION = "x11/wm"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit allarch gtk-icon-cache autotools-brokensep

SRC_URI += "http://downloads.yoctoproject.org/releases/matchbox/utils/xcursor-transparent-theme-${PV}.tar.gz;sha256sum=b26adf2d503d01299718390ae39dab4691a67220de09423be0364e9a060bf7e4 \
    file://0001-Make-Symlinks-Relative.patch \
"

S = "${WORKDIR}/xcursor-transparent-theme-${PV}"

FILES:${PN} += " /usr/share/icons/xcursor-transparent "

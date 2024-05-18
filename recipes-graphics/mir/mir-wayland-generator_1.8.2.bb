SUMMARY = "Wayland generator for the Mir compositor"

require mir.inc

SRC_URI[md5sum] = "d662d1ea8c1f0b34ddd95eb9c6a8a26b"

SRC_URI += " \
    file://0001-Fix-standalone-build.patch \
    file://0001-Fix-cross-build.patch \
    file://0002-Merge-1462.patch \
"

OECMAKE_SOURCEPATH = "${S}/src/wayland/generator"

DEPENDS = "libxml++"

BBCLASSEXTEND = "native nativesdk"

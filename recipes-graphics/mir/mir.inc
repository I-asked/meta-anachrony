SUMMARY ?= "The Mir compositor"
HOMEPAGE = "https://mir-server.io"
LICENSE = "GPL-2.0-only | GPL-3.0-only"

SRC_URI += "https://github.com/MirServer/mir/releases/download/v${PV}/mir-${PV}.tar.xz"

LIC_FILES_CHKSUM = "\
    file://COPYING.GPL2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://COPYING.GPL3;md5=f27defe1e96c2e1ecd4e0c9be8967949 \
    file://COPYING.LGPL2;md5=5f30f0716dfdd0d91eb439ebec522ec2 \
    file://COPYING.LGPL3;md5=e6a600fd5e1d9cbde2d983680233ad02 \
"

S = "${WORKDIR}/mir-${PV}"

FILES:${PN} += "${datadir}"

inherit pkgconfig cmake python3native

DEPENDS += " boost yaml-cpp glib-2.0 util-linux-libuuid glm libdrm virtual/egl udev libinput wayland wayland-native libepoxy glog gflags protobuf-cpp protobuf-cpp-native lttng-ust lttng-ust-native libxcb libxcursor nettle libxml++ freetype libgbm python3-pillow-native virtual/libgles2 mir-wayland-generator-native capnproto-c++ capnproto-c++-native systemtap "

RDEPENDS:${PN} += " xkeyboard-config "

OECMAKE_GENERATOR = "Unix Makefiles"

EXTRA_OECMAKE += " -DMIR_FATAL_COMPILE_WARNINGS=Off -DMIR_ENABLE_TESTS=Off -DMIR_PLATFORM=android -DMIR_WAYLAND_GENERATOR_EXECUTABLE=mir_wayland_generator"

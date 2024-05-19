SUMMARY ?= "The Mir compositor"
HOMEPAGE = "https://mir-server.io"
LICENSE = "GPL-2.0-only | GPL-3.0-only"

SRC_URI += "https://github.com/MirServer/mir/releases/download/v${PV}/mir-${PV}.tar.xz \
            file://0001-Fix-cross-build.patch \
            file://0002-Merge-1462.patch \
            file://0003-Respect-motif-decoration-hint.patch \
            file://ubports/0001-Don-t-print-flout-due-to-bug-in-libhybris-that-make-.patch \
            file://ubports/0002-Nested-patchwork-Use-eglImage-for-software-buffers.patch \
            file://ubports/0003-Use-drmModeGetResources-as-fallback-for-modeset-prob.patch \
            file://ubports/0004-Increase-max-amount-of-data-ints-in-MirBufferPackage.patch \
            file://ubports/0005-Add-10bpp-2bpp-alpha-RGBA-pixel-format-to-MirPixelFo.patch \
            file://ubports/0006-NO-MERGE-Add-envar-to-enable-mirs-own-window-decor.patch \
            file://ubports/0007-DO-NOT-MERGE-Temp-fix-for-wrongly-scaled-buffers.patch \
            file://ubports/0008-wayland-platform-Allow-apply_to-to-set-scale-and-for.patch \
            file://ubports/0009-Revert-Merge-1366.patch \
            file://ubports/0010-Wayland-platform-Compositing-shouldn-t-block.patch \
            file://ubports/0011-Enable-nested-platform-BufferAllocator-to-pass-Wayla.patch \
            file://ubports/0012-nested-make-shared-EGL-context-in-EGLDisplayHandle-c.patch \
            file://ubports/0013-fix-build-on-gcc11.patch \
            file://ubports/0014-Filter-out-some-confusing-libinput-events.patch \
            file://ubports/0015-mesa-Change-scanout-buffer-candidate-calculation.patch \
            file://ubports/0016-miral-add-dsi-output.patch \
           "

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

OECMAKE_GENERATOR = "Unix Makefiles"

EXTRA_OECMAKE += " -DMIR_FATAL_COMPILE_WARNINGS=Off -DMIR_ENABLE_TESTS=Off -DMIR_PLATFORM=android -DMIR_WAYLAND_GENERATOR_EXECUTABLE=mir_wayland_generator"

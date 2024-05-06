SUMMARY = "Graphics platform for the Mir display server to enable the display server on android drivers through the libhybris library."
HOMEPAGE = "https://ubports.com/en"
LICENSE = "GPL-3.0-only"

LIC_FILES_CHKSUM = "\
    file://COPYING.GPL;md5=f27defe1e96c2e1ecd4e0c9be8967949 \
    file://COPYING.LGPL;md5=e6a600fd5e1d9cbde2d983680233ad02 \
"

SRC_URI += "git://gitlab.com/ubports/development/core/hybris-support/mir-android-platform.git;protocol=https;branch=ubports/focal"
SRC_URI += "file://0001-Fix-Missing-Includes.patch"
SRC_URI += "file://0002-Restore-Legacy-HWC-Support.patch"

DEPENDS += " mir android virtual/libgles2"

FILES:${PN} += "${libdir}"

SRCREV = "eb56bd13b6fe93c9e3e94cb254fcf65855791ec2"
PR = "r1"
PV = "+git${SRCPV}"
S = "${WORKDIR}/git"

inherit pkgconfig cmake

# TODO:XXX: Move this to machine-specific overrides
export CPPFLAGS = "-DQCOM_BSP"
export CXXFLAGS = "-DQCOM_BSP"
export CFLAGS = "-DQCOM_BSP"

EXTRA_OECMAKE += " -DMIR_ENABLE_TESTS=Off -DMIR_PLATFORM=android"

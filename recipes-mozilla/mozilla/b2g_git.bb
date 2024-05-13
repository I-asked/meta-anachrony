inherit mozilla

SUMMARY = "Boot to Gecko"
HOMEPAGE = "https://github.com/I-asked/gecko-b2g"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=dc9b6ecd19a14a54a628edaaf23733bf"

SRC_URI += "git://github.com/I-asked/gecko-b2g.git;protocol=https;branch=anachrony"
SRC_URI += "file://0001-Obtain-Rust-Triple-From-OE.patch"

SRCREV = "${AUTOREV}"
PR = "r1"
PV = "+git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS += "curl libevent cairo libnotify gtk+3 \
            virtual/libgles2 pulseaudio icu dbus-glib \
           "

RDEPENDS:${PN} += "api-daemon"

FILES:${PN} = "/opt/b2g"

RUNTIME = "llvm"
LIBCPLUSPLUS = "-stdlib=libc++"
TOOLCHAIN = "clang"

BB_GIT_SHALLOW:pn-b2g = "0"

CARGO_BUILD_FLAGS:append = " --locked "

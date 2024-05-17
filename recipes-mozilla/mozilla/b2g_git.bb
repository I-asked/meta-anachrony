inherit mozilla

SUMMARY = "Boot to Gecko"
HOMEPAGE = "https://github.com/I-asked/gecko-b2g"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=dc9b6ecd19a14a54a628edaaf23733bf"

SRC_URI += "git://github.com/I-asked/gecko-b2g.git;protocol=https;branch=chronology \
            file://0001-Obtain-Rust-Triple-From-OE.patch \
            file://b2g.service \
           "

SRCREV = "${AUTOREV}"
PR = "r1"
PV = "+git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS += "curl libevent cairo libnotify gtk+3 \
            virtual/libgles2 pulseaudio icu dbus-glib \
           "

RDEPENDS:${PN} += "api-daemon"

FILES:${PN} = "/opt/b2g"
FILES:${PN} += " /usr/lib/systemd/user "

RUNTIME = "llvm"
LIBCPLUSPLUS = "-stdlib=libc++"
TOOLCHAIN = "clang"

BB_GIT_SHALLOW:pn-b2g = "0"

CARGO_BUILD_FLAGS:append = " --locked "

do_install:append () {
    install -d ${D}${libdir}/systemd/user/fireplace.service.wants/
    install ${WORKDIR}/b2g.service ${D}${libdir}/systemd/user/b2g.service
    ln -s ../b2g.service ${D}${libdir}/systemd/user/fireplace.service.wants/b2g.service
}

inherit cargo pkgconfig

SUMMARY = "B2G API Daemon"
HOMEPAGE = "https://github.com/I-asked/api-daemon"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI += "git://github.com/I-asked/api-daemon.git;protocol=https;branch=main \
    file://api-daemon.service \
    file://prepare-api-daemon.sh \
"

FILES:${PN} += " /usr/share/api-daemon/ /usr/lib/systemd/user/ /usr/lib/systemd/user/default.target.wants/ ${bindir} "

SRCREV = "${AUTOREV}"
PR = "r1"
PV = "+git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS += "openssl"

CARGO_SRC_DIR = "daemon"
CARGO_DISABLE_BITBAKE_VENDORING = "1"

RUNTIME = "llvm"
LIBCPLUSPLUS = "-stdlib=libc++"
TOOLCHAIN = "clang"

do_configure:append () {
    sed "s,\"third-party\",\"${S}/third-party\"," ${S}/.cargo/config >>${CARGO_HOME}/config
}

do_install:append () {
    export RELEASE_ROOT=${D}/usr/share/api-daemon/http_root/api/v1
    install -d ${RELEASE_ROOT}
    ${S}/release_libs.sh

    install -d ${D}/usr/lib/systemd/user
    install -d ${D}/usr/lib/systemd/user/b2g.service.wants
    install ${WORKDIR}/api-daemon.service ${D}/usr/lib/systemd/user
    ln -sf ../api-daemon.service ${D}/usr/lib/systemd/user/b2g.service.wants/api-daemon.service
}

pkg_postinst:${PN} () {
    setcap "CAP_NET_BIND_SERVICE+ep" $D${bindir}/api-daemon
}

RDEPENDS:${PN} += "libcap"

PACKAGE_WRITE_DEPS = "libcap-native"

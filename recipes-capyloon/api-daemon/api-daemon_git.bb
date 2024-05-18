inherit cargo pkgconfig

SUMMARY = "B2G API Daemon"
HOMEPAGE = "https://github.com/capyloon/api-daemon"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI += "git://github.com/capyloon/api-daemon.git;protocol=https;branch=main \
            file://0001-Make-selinux-optional-on-non-Gonk-Linux-platforms.patch \
            file://0002-Fix-relative-paths-in-release_libs.sh.patch \
            file://0003-Disable-libsignal-in-release_libs.sh.patch \
            file://0004-Do-not-run-rustfmt-during-rebuild.patch \
            file://0005-Fix-b2ghald-not-compiling.patch \
            file://api-daemon.service \
            file://prepare-api-daemon.sh \
           "

FILES:${PN} += " /usr/share/api-daemon/ /usr/lib/systemd/user/ /usr/lib/systemd/user/default.target.wants/ ${bindir} "

SRCREV = "${AUTOREV}"
PR = "r1"
PV = "+git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS += "openssl libcap-native"

CARGO_SRC_DIR = "daemon"
CARGO_DISABLE_BITBAKE_VENDORING = "1"

RUNTIME = "llvm"
LIBCPLUSPLUS = "-stdlib=libc++"
TOOLCHAIN = "clang"

RDEPENDS:${PN} = "libcap-bin"

do_configure:append () {
    sed "s,\"third-party\",\"${S}/third-party\"," ${S}/.cargo/config >>${CARGO_HOME}/config
}

do_install:append () {
    export RELEASE_ROOT=${D}/usr/share/api-daemon/http_root/api/v1
    install -d ${RELEASE_ROOT}
    ${S}/release_libs.sh

    install -d ${D}${bindir}
    cp -dr --preserve=timestamp,mode ${WORKDIR}/prepare-api-daemon.sh ${D}${bindir}/prepare-api-daemon.sh

    install -d ${D}/usr/lib/systemd/user/b2g.service.wants
    install ${WORKDIR}/api-daemon.service ${D}/usr/lib/systemd/user/
    ln -sf ../api-daemon.service ${D}/usr/lib/systemd/user/b2g.service.wants/api-daemon.service

    setcap "CAP_NET_BIND_SERVICE+ep" ${D}${bindir}/api-daemon
    setcap "CAP_NET_BIND_SERVICE+ep" ${D}${bindir}/prepare-api-daemon.sh
}

pkg_postinst_ontarget:${PN} () {
#!/bin/sh
setcap "CAP_NET_BIND_SERVICE+ep" "${bindir}/api-daemon"
setcap "CAP_NET_BIND_SERVICE+ep" "${bindir}/prepare-api-daemon.sh"
}

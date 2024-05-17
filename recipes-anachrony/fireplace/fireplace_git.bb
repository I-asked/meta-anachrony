SUMMARY = "Mir-based Native B2G Frame Host"
HOMEPAGE = "https://github.com/I-asked/fireplace"
LICENSE = "CLOSED"

SRC_URI = "git://github.com/I-asked/fireplace.git;protocol=https;branch=dev"

SRCREV = "${AUTOREV}"
PR = "r1"
PV = "+git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS += "mir"

FILES:${PN} += "/usr/lib/systemd/user"

inherit cmake pkgconfig

do_install:append () {
    install -d ${D}${libdir}/systemd/user/startup-precondition.target.wants/
    install ${B}/fireplace.service ${D}${libdir}/systemd/user/startup-precondition.target.wants/
}

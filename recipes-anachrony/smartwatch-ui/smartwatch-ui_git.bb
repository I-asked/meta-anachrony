SUMMARY = "Smartwatch form factor UI for B2G devices"
HOMEPAGE = "https://github.com/I-asked/smartwatch-ui"
LICENSE = "EUPL-1.2"
LIC_FILES_CHKSUM = "file://NOTICE;md5=c5bae9ebd5a89f602cbe085c510ce1dd"

SRC_URI = "git://github.com/I-asked/smartwatch-ui.git;protocol=https;branch=main"

SRCREV = "${AUTOREV}"
PR = "r1"
PV = "+git${SRCPV}"
S = "${WORKDIR}/git"

FILES:${PN} = "/opt/b2g/webapps/"

PACKAGES = "${PN}"

do_install () {
    install -d ${D}/opt/b2g/webapps/

    cp -dr --preserve=timestamp,mode ${S}/apps/* ${D}/opt/b2g/webapps/
}

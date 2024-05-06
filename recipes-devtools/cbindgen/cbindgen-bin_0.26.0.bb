SUMMARY = "A tool for generating C bindings to Rust code."
HOMEPAGE = "https://github.com/mozilla/cbindgen"
LICENSE = "MPL-2.0"

S = "${WORKDIR}"

SRC_URI += "https://github.com/mozilla/cbindgen/releases/download/${PV}/cbindgen;md5sum=0069006cc975a512dc202a62828d1d24"
SRC_URI += "file://LICENSE"

LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"

fakeroot do_install() {
    install -d ${D}${bindir}
    install -m 755 ${S}/cbindgen ${D}${bindir}
}
do_install[depends] += "virtual/fakeroot-native:do_populate_sysroot"

BBCLASSEXTEND = "native"

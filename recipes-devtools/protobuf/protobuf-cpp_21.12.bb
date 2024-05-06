SUMMARY = "Protocol Buffers - Google's data interchange format"

HOMEPAGE = "https://protobuf.dev/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=37b5762e07f0af8c74ce80a8bda4266b"

SRC_URI += "https://github.com/protocolbuffers/protobuf/releases/download/v${PV}/protobuf-cpp-3.${PV}.tar.gz;md5sum=6b4fd9cee2fa63834f29c7d433679855"

S = "${WORKDIR}/protobuf-3.${PV}"

FILES:${PN}-compiler = "${bindir}"

inherit autotools

PACKAGE_BEFORE_PN = "${PN}-compiler"
RDEPENDS:${PN}-dev = "${PN}-compiler"

do_mypatch () {
    if test -d ${S}/third_party/googletest; then
        mkdir -p ${S}/third_party/googletest/m4
    fi
}
addtask mypatch before do_configure

BBCLASSEXTEND = "native nativesdk"

DEPENDS:pn-protobuf-cpp += " protobuf-cpp-native"
EXTRA_OECONF:pn-protobuf-cpp = "--with-protoc=protoc"

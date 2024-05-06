SUMMARY = "Cap'n Proto serialization/RPC system - core tools and C++ library"

HOMEPAGE = "https://capnproto.org/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=a05663ae6cca874123bf667a60dca8c9"

SRC_URI += "https://capnproto.org/capnproto-c++-${PV}.tar.gz;md5sum=731efe2ff801461d3c68e488aabdfe41"
SRC_URI += "file://0001-Tool-Import-Location.patch"

PROVIDES += "capnproto"

DEPENDS:pn-capnproto-c++ += " capnproto-c++-native"

S = "${WORKDIR}/capnproto-c++-${PV}"

FILES:${PN}-compiler = "${bindir}"

inherit autotools

EXTRA_OECONF:pn-capnproto-c++ = "--with-external-capnp"

PACKAGE_BEFORE_PN = "${PN}-compiler"
RDEPENDS:${PN}-dev = "${PN}-compiler"

BBCLASSEXTEND = "native nativesdk"

INSANE_SKIP_${PN} += " ldflags"
SOLIBS = ".so"
FILES_SOLIBSDEV = " \
    /usr/lib/libkj-test.so \
    /usr/lib/libcapnp-json.so \
    /usr/lib/libkj.so \
    /usr/lib/libcapnp.so \
    /usr/lib/libcapnpc.so \
    /usr/lib/libcapnp-rpc.so \
    /usr/lib/libkj-async.so \
    /usr/lib/libkj-http.so \
    /usr/lib/libcapnp-websocket.so \
"

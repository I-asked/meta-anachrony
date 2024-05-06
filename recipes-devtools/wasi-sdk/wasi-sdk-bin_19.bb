inherit clang
inherit clang-native

SUMMARY = "WASI-enabled WebAssembly C/C++ toolchain"
HOMEPAGE = "https://github.com/WebAssembly/wasi-sdk"

SRC_URI += "https://github.com/WebAssembly/wasi-sdk/releases/download/wasi-sdk-${PV}/wasi-sdk-${PV}.0-linux.tar.gz;md5sum=815b177a1aba3502752f7ad471275a76"
SRC_URI += "file://LICENSE"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a1ba2b4c4f909ac0b517d8a37d2ac70f"

RUNTIME = "llvm"
TOOLCHAIN = "clang"

S = "${WORKDIR}"

fakeroot do_install() {
    install -d ${D}${datadir}/wasi-sysroot
    cp -dr --preserve=mode,timestamp ${S}/wasi-sdk-${PV}.0/* ${D}${datadir}/wasi-sysroot/
    rm -rf ${D}${datadir}/wasi-sysroot/usr
    ln -sf . ${D}${datadir}/wasi-sysroot/usr
}
do_install[depends] += "virtual/fakeroot-native:do_populate_sysroot"

BBCLASSEXTEND = "native"

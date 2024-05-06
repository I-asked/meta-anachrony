inherit clang
inherit clang-native

SUMMARY = "WASI libc"
HOMEPAGE = "https://github.com/WebAssembly/wasi-libc"
SRC_URI += "git://github.com/WebAssembly/wasi-libc.git;protocol=https;branch=main"

LICENSE = "Apache-2.0-with-LLVM-exception"
LIC_FILES_CHKSUM = "file://LICENSE;md5=234aa41af73fce9d38421795d4641dfc"

SRCREV = "${AUTOREV}"
PR = "r1"
PV = "+git${SRCPV}"
S = "${WORKDIR}/git"

RUNTIME = "llvm"
TOOLCHAIN = "clang"

do_compile () {
    oe_runmake WASM_CC="${BUILD_CC}" \
               WASM_AR="${BUILD_AR}" \
               WASM_NM="${BUILD_NM}"
}

fakeroot do_install() {
    install -d ${D}${datadir}/wasi-sysroot
    cp -dr --preserve=mode,timestamp sysroot/* ${D}${datadir}/wasi-sysroot/
}
do_install[depends] += "virtual/fakeroot-native:do_populate_sysroot"

BBCLASSEXTEND = "native"

inherit clang clang-native

CLANG_VERSION = "15.0.7"

SUMMARY = "WASI-enabled WebAssembly C/C++ toolchain"
HOMEPAGE = "https://github.com/WebAssembly/wasi-sdk"

SRC_URI += "https://github.com/WebAssembly/wasi-sdk/releases/download/wasi-sdk-${PV}/wasi-sdk-${PV}.0-linux.tar.gz;md5sum=815b177a1aba3502752f7ad471275a76 \
    file://LICENSE \
"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a1ba2b4c4f909ac0b517d8a37d2ac70f"

RUNTIME = "llvm"
TOOLCHAIN = "clang"

PACKAGES = "${PN}"

S = "${WORKDIR}"

FILES:${PN} = "/usr/share/wasi-sysroot"

SYSROOT_DIRS_NATIVE += "/usr/share/wasi-sysroot"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

do_install() {
    install -d ${D}/usr/share/
    cp -dr --preserve=mode,timestamp ${S}/wasi-sdk-${PV}.0/share/wasi-sysroot ${D}/usr/share/wasi-sysroot

    install -d ${D}/usr/share/wasi-sysroot/usr/lib/
    cp -dr --preserve=mode,timestamp ${S}/wasi-sdk-${PV}.0/lib/clang ${D}/usr/share/wasi-sysroot/usr/lib/
}

INSANE_SKIP:${PN} += "staticdev"

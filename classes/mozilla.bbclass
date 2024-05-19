TUNE_FEATURES:append = " armv7a"

inherit clang clang-native rust-common pkgconfig

DEPENDS += "gnu-config-native virtual/libintl zip-native \
            wasi-sdk-bin wasi-libc-native \
            rust-native cargo-native libstd-rs \
            gcc-runtime libcxx-native libcxx\
            cbindgen-bin-native unzip-native \
"

SRC_URI += "file://mozconfig"

EXTRA_OECONF = "--target=${TARGET_SYS} --host=${BUILD_SYS} \
                --with-toolchain-prefix=${TARGET_SYS}- \
                --prefix=${prefix} \
                --libdir=${libdir}"
EXTRA_OECONF:append:arm = " --disable-elf-hack"
EXTRA_OECONF:append:x86 = " --disable-elf-hack"
EXTRA_OECONF:append:x86-64 = " --disable-elf-hack"
SELECTED_OPTIMIZATION = "-Os -fsigned-char -fno-strict-aliasing"

SYSROOT_DIRS_NATIVE += "${prefix}"
SYSROOT_DIRS_IGNORE += "\
    ${prefix}/share \
    ${prefix}/etc \
"

export CXXSTDLIB = "c++"

export CROSS_COMPILE = "1"
export MOZCONFIG = "${B}/my-mozconfig"
export MOZ_OBJDIR = "${B}/gecko-build-dir"

export BUILD_LDFLAGS += " -fuse-ld=lld "
export BUILD_CFLAGS += " -fuse-ld=lld "

export AS = "${CC}"

export HOST_AS = "${BUILD_CC}"
export HOST_CC = "${BUILD_CC}"
export HOST_CXX = "${BUILD_CXX}"
export HOST_ASFLAGS = "${BUILD_ASFLAGS}"
export HOST_CFLAGS = "${BUILD_CFLAGS}"
export HOST_CXXFLAGS = "${BUILD_CXXFLAGS}"
export HOST_LDFLAGS = "${BUILD_LDFLAGS}"
export HOST_RANLIB = "${BUILD_RANLIB}"
export HOST_AR = "${BUILD_AR}"

mozilla_run_mach() {
    export SHELL="/bin/sh"
    export RUSTFLAGS="${RUSTFLAGS} -lc++ -Cpanic=abort"

    export RUST_HOST="${RUST_BUILD_SYS}"
    export RUST_TARGET="${RUST_HOST_SYS}"
    export BINDGEN_MFLOAT="${@bb.utils.contains('TUNE_CCARGS_MFLOAT', 'hard', '-mfloat-abi=hard', '', d)}"
    export BINDGEN_CFLAGS="--target=${TARGET_SYS} --sysroot=${RECIPE_SYSROOT} ${BINDGEN_MFLOAT}"

    export INSTALL_SDK=0
    export DESTDIR="${D}"

    export RUST_BUILD="${RUST_BUILD_SYS}"
    export RUST_TARGET="${RUST_TARGET_SYS}"

    ./mach "$@"
}

mozilla_do_configure() {
    install -D -m 0644 ${WORKDIR}/mozconfig ${MOZCONFIG}
    if [ ! -z "${EXTRA_OECONF}" ] ; then
        for f in ${EXTRA_OECONF}
        do
            echo ac_add_options $f >> ${MOZCONFIG}
        done
    fi
    if [ ! -z "${PACKAGECONFIG_CONFARGS}" ] ; then
        for f in ${PACKAGECONFIG_CONFARGS}
        do
            echo ac_add_options $f >> ${MOZCONFIG}
        done
    fi
    echo ac_add_options --enable-optimize=\"${SELECTED_OPTIMIZATION}\" \
        >> ${MOZCONFIG}
    echo ac_add_options --with-wasi-sysroot=\"${RECIPE_SYSROOT}/usr/share/wasi-sysroot\" \
        >> ${MOZCONFIG}
    if [ ! -z "${CCACHE_DIR}" ] ; then
        echo ac_add_options --with-ccache >> ${MOZCONFIG}
    fi

    mozilla_run_mach configure
}

mozilla_do_compile() {
    mozilla_run_mach build --verbose
}

mozilla_do_install() {
    rm -f ${MOZ_OBJDIR}/dist/*.tar.bz2

    mozilla_run_mach package

    install -d ${D}/opt
    tar -C ${D}/opt/ -xjf ${MOZ_OBJDIR}/dist/*.tar.bz2
}

do_configure[network] = "1"

EXPORT_FUNCTIONS do_configure do_compile do_install run_mach

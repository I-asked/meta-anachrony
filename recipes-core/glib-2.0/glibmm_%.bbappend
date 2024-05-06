BBCLASSEXTEND += " native"

do_write_config:append() {
    cat >${WORKDIR}/meson-${PN}.cross <<EOF
[binaries]
m4 = '${bindir}/m4'
perl = '${bindir}/perl'
pkgconfig = 'pkg-config'
EOF
}

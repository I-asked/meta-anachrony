BBCLASSEXTEND += " native"

do_install:append() {
        sed -i -e '1s,#!.*python.*,#!/usr/bin/env python3,' ${D}${bindir}/lttng-gen-tp
}

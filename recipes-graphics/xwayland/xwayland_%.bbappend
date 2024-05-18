FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += " file://0001-hw-Integrate-hybris-backend-for-glamor.patch \
    file://0002-glamor-Remove-one-OpenGL-extension-requirement.patch \
    file://0003-glamor-Enable-EGL-to-work-on-Android-Hybris-buffers-.patch \
    file://0004-glamor-fix-rb-swap-and-picture-lost.patch \
    file://0005-hw-xwayland-glamor-Enable-xwayland-present-for-more-.patch \
"

REQUIRED_DISTRO_FEATURES:remove = "opengl"

DEPENDS:append = " drihybris libxshmfence "

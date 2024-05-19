FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

REQUIRED_DISTRO_FEATURES:append = " x11 "

DEPENDS += " libxcb xorgproto xtrans pixman libxkbfile libxfont2 libdrm libxcvt libxshmfence libxext "

SRC_URI += " \
            file://0001-Allow-window-system-to-hook-eglGetConfigAttrib-neede.patch;striplevel=2 \
            file://0002-Re-implement-X11-EGL-platform-based-on-wayland-code.patch;striplevel=2 \
            file://0003-Add-X11NativeWindow-getUsage-according-to-NativeWind.patch;striplevel=2 \
            file://0004-Use-custom-DRIHYBRIS-Xorg-extension-for-buffer-shari.patch;striplevel=2 \
            file://0005-x11nativewindow-check-for-window-resizes-using-Prese.patch;striplevel=2 \
            file://0006-x11nativewindow-use-same-depth-as-target-window-for-.patch;striplevel=2 \
            file://0007-Decouple-drihybris-from-present-extension-detection.patch;striplevel=2 \
            file://0008-x11nativewindow-support-gralloc1-by-relying-on-libhy.patch;striplevel=2 \
            file://0009-Add-missing-includes-to-the-X11-EGL-platform.patch;striplevel=2 \
            file://0010-Make-eglGetProcAddress-dlsym-before-slave-dispatch.patch;striplevel=2 \
           "

EXTRA_OECONF += " --enable-debug --enable-trace "

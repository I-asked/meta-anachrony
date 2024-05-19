inherit core-image
inherit extrausers
LICENSE = "GPL-2.0-only"

IMAGE_FEATURES += "package-management debug-tweaks"

IMAGE_INSTALL += " \
kernel-modules base-files base-passwd systemd busybox iproute2 connman pam-plugin-loginuid bluez5 polkit polkit-group-rule-datetime \
pulseaudio-server openssh-sshd openssh-sftp-server openssh-scp dsme mce ngfd nfcd resize-rootfs usb-moded ofono sensorfw \
${@oe.utils.conditional('MACHINE_HAS_WLAN', 'true', 'iproute2 wpa-supplicant connman-client', '', d)} \
tomtenisse-misc smartwatch-ui b2g fireplace mir mesa xcursor-transparent-theme \
"

IMAGE_INSTALL:remove:hybris-machine = "mesa"
IMAGE_INSTALL:append:hybris-machine = " mce-plugin-libhybris mir-android-platform drihybris "

IMAGE_INSTALL:remove = "sensorfw-hybris-hal-plugins asteroid-hrm buteo-mtp"

EXTRA_USERS_PARAMS = "groupadd system; \
                      groupadd gps; \
                      groupadd datetime; \
                      groupadd -f -g 1024 mtp; \
                      useradd -p '' -G 'audio,video,system,wheel,gps,datetime,mtp,users' ceres"

IMAGE_OVERHEAD_FACTOR = "1.0"
IMAGE_ROOTFS_EXTRA_SPACE = "131072"

EXTRA_IMAGECMD:ext4 += " -O^metadata_csum"

DEPENDS:append = " ${@oe.utils.conditional('GENERATE_SPARSE_IMAGE', 'true', 'android-simg2img-native', '', d)} "

generate_sparse_image() {
    if [ -n "${GENERATE_SPARSE_IMAGE}" ]; then
        img2simg "${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ext4" "${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.simg"
        ln -s "${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.simg" "${IMGDEPLOYDIR}/${IMAGE_LINK_NAME}.simg"
    fi
}

IMAGE_POSTPROCESS_COMMAND:append = " generate_sparse_image ; "

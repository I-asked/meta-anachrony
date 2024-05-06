# we add back the armv7a tune here which has been removed in meta-openenembedded
# the tune itself is not needed, but the rust.inc file uses the TUNE_FEATURES
# value for parsing compile parameters. Can be removed if fixed in meta-rust
TUNE_FEATURES:append = " armv7a"

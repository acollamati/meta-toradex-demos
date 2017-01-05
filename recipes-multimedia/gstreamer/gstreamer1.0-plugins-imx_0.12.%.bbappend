# when using the libimxvpuapi the resulting plugins causes asserts when using
# $ gst-play-1.0 a-video-file
# e.g.: gst-launch-1.0: ../imxvpuapi/imxvpuapi_vpulib.c:2349: imx_vpu_dec_mark_framebuffer_as_displayed: Assertion `framebuffer != ((void *)0)' failed.
# not building the vpu plugins works around the issue
PACKAGECONFIG_remove = "vpu"

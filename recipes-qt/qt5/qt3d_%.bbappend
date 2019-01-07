# qt3d links against the opengl flavours we configured qtbase for, so
# depend on them here
DEPENDS_GLES = ""
DEPENDS_GLES_imxpxp += "virtual/libgles2 virtual/egl"
DEPENDS_GLES_imgpu3d += "virtual/libgles2 virtual/egl"
DEPENDS_GLES_use-mainline-bsp += "virtual/libgles2 virtual/egl"

DEPENDS_class-target += " ${DEPENDS_GLES}"


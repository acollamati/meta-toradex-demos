# Build OpenGL/ES support if available

QT_GLFLAGS_colibri-t20 = "-opengl es2 "
QT_GLFLAGS_colibri-t30 = "-opengl es2 "
QT_GLFLAGS_apalis-t30 = "-opengl es2 "

DEPENDS_append_colibri-t20 = " virtual/libgles2"
DEPENDS_append_colibri-t30 = " virtual/libgles2"
DEPENDS_append_apalis-t30 = " virtual/libgles2"

# Use default packageconfig but .NET Core 2.0 prefers OpenSSL backend
PACKAGECONFIG_remove = "gnutls"
PACKAGECONFIG_append = " ssl"

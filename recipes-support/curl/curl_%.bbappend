# Use default packageconfig but .NET Core 2.0 prefers OpenSSL backend
PACKAGECONFIG:remove = "gnutls"
PACKAGECONFIG:append = " ssl"

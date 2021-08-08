DESCRIPTION = "Provides runtime dependencies for .NET Core 2.0"

inherit packagegroup

PACKAGES = "packagegroup-dotnet-deps"

RDEPENDS:packagegroup-dotnet-deps = "\
    libunwind \
    icu \
    libcurl \
"

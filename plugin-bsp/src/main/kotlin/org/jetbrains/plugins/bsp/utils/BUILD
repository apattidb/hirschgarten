load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "utils",
    srcs = glob(["*.kt"]),
    visibility = ["//plugin-bsp:__subpackages__"],
    deps = [
        "@rules_intellij//intellij_platform_sdk:plugin_api",
    ],
)

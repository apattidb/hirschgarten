load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "python",
    srcs = glob([
        "**/*.kt",
    ]),
    visibility = ["//visibility:public"],
    deps = [
        "//plugin-bsp/src/main/kotlin/org/jetbrains/plugins/bsp/config",
        "//plugin-bsp/src/main/kotlin/org/jetbrains/plugins/bsp/extensionPoints",
        "//plugin-bsp/workspacemodel/src:workspacemodel",
        "//protocol",
        "@rules_intellij//intellij_platform_sdk:plugin_api",
        "@rules_intellij//third_party/python",
    ],
)

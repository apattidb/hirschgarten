load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "intellij",
    srcs = glob([
        "**/*.kt",
    ]),
    visibility = ["//visibility:public"],
    deps = [
        "//plugin-bsp/protocol/src/main/kotlin/org/jetbrains/bsp/protocol/utils",
        "//plugin-bsp/src/main/kotlin/org/jetbrains/plugins/bsp/config",
        "//plugin-bsp/src/main/kotlin/org/jetbrains/plugins/bsp/impl",
        "//plugin-bsp/src/main/kotlin/org/jetbrains/plugins/bsp/run",
        "//plugin-bsp/workspacemodel/src:workspacemodel",
        "//protocol",
        "@maven//:ch_epfl_scala_bsp4j",
        "@maven//:com_google_code_gson_gson",
        "@rules_intellij//intellij_platform_sdk:plugin_api",
        "@rules_intellij//third_party/devkit",
    ],
)

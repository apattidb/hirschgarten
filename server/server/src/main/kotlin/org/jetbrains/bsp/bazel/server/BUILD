load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_binary", "kt_jvm_library")

kt_jvm_library(
    name = "server",
    srcs = glob([
        "**/*.kt",
    ]),
    visibility = [
        #         "//server/bspcli:__subpackages__",
        #         "//server/install:__subpackages__",
        #         "//server/server:__subpackages__",
        "//visibility:public",
    ],
    deps = [
        "//server/bazelrunner",
        "//server/commons",
        "//server/executioncontext/workspacecontext",
        "//server/logger",
        "//server/server/src/main/kotlin/org/jetbrains/bsp/bazel/server/bep",
        "//server/server/src/main/kotlin/org/jetbrains/bsp/bazel/server/bsp",
        "//server/server/src/main/kotlin/org/jetbrains/bsp/bazel/server/bsp/info",
        "//server/server/src/main/kotlin/org/jetbrains/bsp/bazel/server/bsp/managers",
        "//server/server/src/main/kotlin/org/jetbrains/bsp/bazel/server/bsp/utils",
        "//server/server/src/main/kotlin/org/jetbrains/bsp/bazel/server/diagnostics",
        "//server/server/src/main/kotlin/org/jetbrains/bsp/bazel/server/sync",
        "@maven//:ch_epfl_scala_bsp4j",
        "@maven//:org_apache_logging_log4j_log4j_api",
        "@maven//:org_apache_logging_log4j_log4j_core",
        "@maven//:org_eclipse_lsp4j_org_eclipse_lsp4j_jsonrpc",
    ],
)

java_binary(
    name = "bazel-bsp",
    main_class = "org.jetbrains.bsp.bazel.server.ServerInitializer",
    visibility = ["//visibility:public"],
    runtime_deps = [
        ":server",
        "@maven//:ch_epfl_scala_bsp4j",
        "@maven//:org_apache_velocity_velocity_engine_core",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:com_intellij_platform_kotlinx_coroutines_core_jvm",
    ],
)

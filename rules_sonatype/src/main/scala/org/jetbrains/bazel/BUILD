load("@io_bazel_rules_scala//scala:scala.bzl", "scala_binary")

scala_binary(
    name = "SonatypePublisher",
    srcs = glob([
        "*.scala",
        "sonatype/*.scala",
    ]),
    main_class = "org.jetbrains.bazel.SonatypePublish",
    scalacopts = [
        "-encoding",
        "UTF-8",
        "-deprecation",
        "-unchecked",
    ],
    visibility = ["//visibility:public"],
    deps = [
        "@rules_sonatype_deps//:org_apache_httpcomponents_httpclient",
        "@rules_sonatype_deps//:org_backuity_clist_clist_core_2_13",
        "@rules_sonatype_deps//:org_backuity_clist_clist_macros_2_13",
        "@rules_sonatype_deps//:org_scala_lang_scala_library",
        "@rules_sonatype_deps//:org_scala_lang_scala_reflect",
        "@rules_sonatype_deps//:org_sonatype_spice_zapper_spice_zapper",
        "@rules_sonatype_deps//:org_wvlet_airframe_airframe_codec_2_13",
        "@rules_sonatype_deps//:org_wvlet_airframe_airframe_control_2_13",
        "@rules_sonatype_deps//:org_wvlet_airframe_airframe_http_2_13",
        "@rules_sonatype_deps//:org_wvlet_airframe_airframe_log_2_13",
    ],
)

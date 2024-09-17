#!/bin/bash -eu

build() {
  platform="$1"
  file_suffix="$2"

  bazel build --platforms="${platform}" //server/server/src/main/kotlin/org/jetbrains/bsp/bazel/server:bazel-bsp
  pushd bazel-bin/server/server/src/main/kotlin/org/jetbrains/bsp/bazel/server
  fname="/tmp/bazel-bsp-${file_suffix}.zip"
  rm -f "${fname}"
  echo "Writing ${fname}"
  zip -q -r "${fname}" bazel-bsp bazel-bsp.runfiles
  popd
}

build //release:linux-x86_64 linux-amd64
build //release:macos-arm64 darwin-arm64

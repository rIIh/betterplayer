#!/bin/bash

BUILD_VERSION="${1:-$BUILD_VERSION}"

if [[ $BUILD_VERSION ]];
then
    sed -i -r "s/^version: .+/version: $BUILD_VERSION/gm" pubspec.yaml || sed -i '' -E "s/^version: .+/version: $BUILD_VERSION/g" pubspec.yaml
    echo "Version updated"
    cat pubspec.yaml | grep "^version: "
fi

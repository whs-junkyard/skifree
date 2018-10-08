# Skifree

This probably was my first Java project back in 2011.

The source was from a decompiled jar, reformatted and cleaned of
unused variables/imports.

## Building

The original probably didn't use any build tools. Now it is built with
[Bazel](https://bazel.build/)

To run: `bazel run skifree`

To build jar: `bazel build skifree_deploy.jar`. The output will be in bazel-bin/skifree_deploy.jar

## License

You probably don't want to use this code.

Thanks to @PhompAng for helping me getting the original jar to run.

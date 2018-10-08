# Skifree

This probably was my first Java project back in 2011.

The source was from a decompiled jar, reformatted and cleaned of
unused variables/imports.

## How to play
The game requires a 80x25 characters terminal. It will stop at the
starting screen until you have resized your terminal to fit the window.

Run around, don't hit #, collect L.

Press A to steer left. D to steer right.

## Building

The original probably didn't use any build tools. Now it is built with
[Bazel](https://bazel.build/)

To run: `bazel run skifree`

To build jar: `bazel build skifree_deploy.jar`. The output will be in bazel-bin/skifree_deploy.jar

The internet high score server is no longer running, and I have lost
the original source

## License

You probably don't want to use this code.

Thanks to @PhompAng for helping me getting the original jar to run.

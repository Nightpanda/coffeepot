#! /bin/bash
# Runs CLJ Kendo in docker
docker run -v $PWD/src:/src --rm borkdude/clj-kondo clj-kondo --lint src

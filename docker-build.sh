#! /bin/bash
sbt "scalafmtAll; scalafixAll; clean; Docker/publishLocal"

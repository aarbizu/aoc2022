build:
	./gradlew build spotlessCheck installDist idea

run:
	./build/install/aoc2022/bin/aoc2022

tasks:
	./gradlew tasks

.PHONY: build

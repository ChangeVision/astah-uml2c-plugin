.PHONY: all
all: build

.PHONY: build
build:
	astah-build

.PHONY: test
test:
	astah-mvn test

.PHONY: launch
launch: build
	astah-launch

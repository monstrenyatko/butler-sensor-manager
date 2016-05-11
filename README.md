BUTLER-SENSOR-MANAGER
=====================

About
=====

Sensors based on [butler-arduino-sensor](https://github.com/monstrenyatko/butler-arduino-sensor)
publish data to `MQTT` broker.

[butler-sensor-manager](https://github.com/monstrenyatko/butler-sensor-manager)
performs processing of the published information and stores compatible data types to 
[InfluxDB](https://influxdata.com/time-series-platform/influxdb/). `InfluxDB` is
an open source database specialized on handling time series data with high availability
and high performance requirements.

Prepare build environment
=========================

Java Development Kit
--------------------

Install Oracle JDK 8 or later.

Apache Maven
------------
Install [Apache Maven](https://maven.apache.org) version `3.2.2` or later.

Building
========

Raspberry Pi
------------

Building package for [Raspberry Pi](https://www.raspberrypi.org).

- Go to the `project directory`:
```sh
cd <project directory>
```
- Use `Maven` to start build:
```sh
mvn package
```
- The resulting `deb` file is located in `<project directory>/target` directory.
See `<name>-<version>-all.deb` file.

Usage
=====

TBD

Configuration
=============

TBD

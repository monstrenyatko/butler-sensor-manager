#! /bin/sh

exec env BUTLER_SENSOR_MANAGER_CONFIG_DIR=$2 java -jar $1

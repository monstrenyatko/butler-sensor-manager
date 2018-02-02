# HOW TO DOCKER

## About

Run using [Docker](https://www.docker.com).

## Build the container

* Place the `deb` package to install directory
* Execute build script:
```sh
	./build.sh
```

## Run

* Create `Data` storage:
```sh
	BUTLER_SENSOR_MANAGER_DATA="butler-sensor-manager-data"
	docker volume create --name $BUTLER_SENSOR_MANAGER_DATA
```
* Create `Configuration` storage:
```sh
	BUTLER_SENSOR_MANAGER_CFG="butler-sensor-manager-config"
	docker volume create --name $BUTLER_SENSOR_MANAGER_CFG
```
* Copy `Configuration` to the storage:
```sh
	docker run -v $BUTLER_SENSOR_MANAGER_CFG:/mnt --rm -v $(pwd):/src hypriot/armhf-busybox \
		sh -c "cp /src/config/* /mnt/"
```
* Edit `Configuration` (OPTIONAL):
```sh
	docker run -v $BUTLER_SENSOR_MANAGER_CFG:/mnt --rm -it hypriot/armhf-busybox \
		vi /mnt/config.json
```
* Start prebuilt image:
```sh
	docker-compose up -d --no-build
```
* Stop/Restart:
```sh
	docker stop butler-sensor-manager
	docker start butler-sensor-manager
```

# master-slave 구성 실습
replica-up:
	docker-compose -f docker-compose-replica.yml up -d --force-recreate --build

replica-down:
	docker-compose -f docker-compose-replica.yml down -v


# sentinel 구성 실습 (실환경처럼 구성을 하도록 노력했지만 실패하여 가이드 그대로 진행)
sentinel-up:
	docker-compose -f docker-compose-sentinel.yml up -d --force-recreate --build --scale redis-sentinel=3

sentinel-down:
	docker-compose -f docker-compose-sentinel.yml down -v


# 배포 환경 구성
prod-up:
	docker-compose up -d --force-recreate --build --scale redis-sentinel=3

prod-down:
	docker-compose down -v

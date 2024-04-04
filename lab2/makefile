
all: semaphore bus

semaphore: es1.c semaphore.c
	gcc es1.c semaphore.c -o semaphore -lpthread

bus:       es2.c
	gcc es2.c             -o bus       -lpthread

clear: 
	rm semaphore bus

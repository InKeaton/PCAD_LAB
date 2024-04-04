
all: barrier philo1 philo2

barrier: barrier.c
	gcc barrier.c -o barrier -lpthread

philo1: philo1.c
	gcc  philo1.c -o philo1  -lpthread -fsanitize=thread -g

philo2: philo2.c
	gcc  philo2.c -o philo2  -lpthread -fsanitize=thread -g

clear: 
	rm barrier philo2 philo1 

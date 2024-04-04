#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <pthread.h>
#include <stdbool.h>

typedef struct {
	unsigned int c_posti;
	unsigned int posti_presi;
	pthread_mutex_t bus_lock;
	pthread_cond_t 	exit_cond;
	pthread_cond_t	enter_cond;
	pthread_cond_t 	journey_cond;
} Bus;

////////////////////////////////////////////////////////////////////////////////////////////
/*	
 *	--PROBLEMA DEL BUS CON VARIABILI CONDIZIONALI (BARRIER LIKE)--
*/
////////////////////////////////////////////////////////////////////////////////////////////

int life_bus(Bus* b) {
	pthread_mutex_lock(&b->bus_lock);
		// wait to start
		pthread_cond_wait(&b->journey_cond, &b->bus_lock);
		// travel
		sleep(3);
		// signal to get off the bus
		pthread_cond_broadcast(&b->exit_cond);
	pthread_mutex_unlock(&b->bus_lock);
	
	return 0;
}

int init_bus(Bus* b, unsigned int posti) {
	if(posti == 0) return 1;
	b->c_posti = posti;
	b->posti_presi = 0;
	pthread_mutex_init(&b->bus_lock, NULL);
	return 0;
}

int enter_bus(Bus* b) {
	pthread_mutex_lock(&b->bus_lock);
		while(b->posti_presi == b->c_posti) {
			// all seats are taken
			pthread_cond_wait(&b->enter_cond, &b->bus_lock);
		}
		// some seats are free, i take one
		// if i am the last one, i signal to start
		if(++b->posti_presi == b->c_posti) {
			pthread_cond_signal(&b->journey_cond);
		}
		// i'm on board, i sleep
		pthread_cond_wait(&b->exit_cond, &b->bus_lock);
		// i get off the bus, 
		// if i am the last one i signal that others can get on 
		if(--b->posti_presi == 0) {
			pthread_cond_signal(&b->enter_cond);
		}
	pthread_mutex_unlock(&b->bus_lock);
}

int main(int argc, char** args) {
	const int n_pass = 10, c_posti = 5;
	Bus b;

	init_bus(&b, c_posti);
}

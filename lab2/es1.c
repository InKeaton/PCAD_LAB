#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <pthread.h>
#include <stdbool.h>

#include "semaphore.h"

////////////////////////////////////////////////////////////////////////////////////////////
/*	
 *	--VERSIONE DEL PROBLEMA DEI FILOSOFI CON SEMAFORI--
*/
////////////////////////////////////////////////////////////////////////////////////////////
#define NUMPHILO 5


my_semaphore stick[NUMPHILO];
my_semaphore waiting_room;

void* Filosofo(void* i) {
	unsigned volatile int numPhi = *(int*) i;
	for(int mangia = 0; mangia < NUMPHILO; mangia++) {
		//pre-protocollo
		unsigned volatile int bSx, bDx = numPhi; 
		bSx = (numPhi-1+NUMPHILO)%NUMPHILO;
		my_sem_wait(&waiting_room);
		
		//RACE MOMENT
		printf("%d , %d | Cerco di prendere la bacchetta sinistra (%d) \n", numPhi, mangia, bSx);
		my_sem_wait(&stick[bSx]);
			printf("%d | Prendo la bacchetta sinistra (%d) \n", numPhi, bSx);
			printf("%d | Cerco di prendere la bacchetta destra (%d) \n", numPhi, bDx);
			my_sem_wait(&stick[bDx]);
				printf("%d | Prendo la bacchetta destra (%d) \n", numPhi, bDx);
				printf("%d | Mangio....... :))\n", numPhi);
				printf("%d | Finito di mangiare....... :))\n", numPhi);
			my_sem_signal(&stick[bDx]);
		my_sem_signal(&stick[bSx]);
	
		//post-protocollo
		my_sem_signal(&waiting_room);
		printf("%d | Rilascio entrambe la bacchette :) \n", numPhi);
	}
	return NULL;
}

int main(int arg, char** args) {
	pthread_t philo[NUMPHILO];
	int ar[NUMPHILO];
	my_sem_init(&waiting_room, 4);
	for(unsigned int i = 0; i < NUMPHILO; i++) { 
		my_sem_init(&stick[i], 1);
		ar[i] = i; 
	}

	for(unsigned int i = 0; i < NUMPHILO; i++) {
		if(pthread_create(&philo[i], NULL, Filosofo,  &ar[i]) != 0) { 
			return EXIT_FAILURE; 	
		}
	}

	for(int i = 0; i < NUMPHILO; i++) {
		if(pthread_join(philo[i], NULL) != 0) {
			return EXIT_FAILURE; 
		}
	}

	for(int i = 0; i < NUMPHILO; i++) {
		my_sem_destroy(&stick[i]);
	}
	my_sem_destroy(&waiting_room);
	printf("END :|\n");
	return 0;
}


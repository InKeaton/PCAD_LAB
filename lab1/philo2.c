#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <pthread.h>
#include <stdbool.h>

////////////////////////////////////////////////////////////////////////////////////////////
/*	
 *	--VERSIONE DEL PROBLEMA DEI FILOSOFI SENZA DEADLOCK--
*/
////////////////////////////////////////////////////////////////////////////////////////////
#define NUMPHILO 5

struct Neighbor {
	volatile bool array[NUMPHILO];
	pthread_mutex_t neighborLock;
};


pthread_mutex_t mutexThread[NUMPHILO];
struct Neighbor neigh;
int eat[NUMPHILO];

void* Filosofo(void* i) {
	unsigned volatile int numPhi = *(int*) i;
	for(int mangia = 0; mangia < NUMPHILO; mangia++) {
		//pre-protocollo
		unsigned volatile int bSx, bDx = numPhi; 
		bSx = (numPhi-1+NUMPHILO)%NUMPHILO;
		bool eat = false;

		while(!eat) {
			pthread_mutex_lock(&neigh.neighborLock);
				if(!neigh.array[bSx] && !neigh.array[bDx]) {
					neigh.array[bSx] = 1;
					neigh.array[bSx] = 1;
					eat = 1;
				}
			pthread_mutex_unlock(&neigh.neighborLock);
			if(!eat){ 
				printf("%d | Sta pensando :////\n", numPhi);
			}
		}
	
		//RACE MOMENT
		printf("%d , %d | Cerco di prendere la bacchetta sinistra (%d) \n", numPhi, mangia, bSx);
		pthread_mutex_lock(&mutexThread[bSx]);
			printf("%d | Prendo la bacchetta sinistra (%d) \n", numPhi, bSx);
			printf("%d | Cerco di prendere la bacchetta destra (%d) \n", numPhi, bDx);
			pthread_mutex_lock(&mutexThread[bDx]);
				printf("%d | Prendo la bacchetta destra (%d) \n", numPhi, bDx);
				printf("%d | Mangio....... :))\n", numPhi);
				printf("%d | Finito di mangiare....... :))\n", numPhi);
			pthread_mutex_unlock(&mutexThread[bDx]);
		pthread_mutex_unlock(&mutexThread[bSx]);
	
		//post-protocollo
		pthread_mutex_lock(&neigh.neighborLock);
			neigh.array[bSx] = 0;
			neigh.array[bSx] = 0;
		pthread_mutex_unlock(&neigh.neighborLock);
		printf("%d | Rilascio entrambe la bacchette :) \n", numPhi);
	}

}

int main(int arg, char** args) {
	pthread_t philo[NUMPHILO];
	int ar[NUMPHILO];
	pthread_mutex_init(&neigh.neighborLock, NULL);

	for(unsigned int i = 0; i < NUMPHILO; i++) { 
		pthread_mutex_init(&mutexThread[i], NULL);
		neigh.array[i] = false;
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
	printf("END :|\n");
	return 0;
}

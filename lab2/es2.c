#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <pthread.h>
#include <stdbool.h>

//pre-processor variable define
#ifndef NUMPOSTIBUS
#define NUMPOSTIBUS  5
#endif

#ifndef NUMGIRI
#define NUMGIRI 	 10
#endif

#define NUMPASS NUMPOSTIBUS*3
#define NUMVIAGGI (NUMPOSTIBUS/NUMPASS) * NUMGIRI


typedef struct {
	volatile unsigned int c_posti;			// indica il numero di posti presenti all'interno del bus
	volatile unsigned int posti_presi;	    // indica il numero di posti presi nel bus
	volatile unsigned int up; 			    // se il valore è maggiore di 0 si può salire nel bus sennò no
	pthread_mutex_t bus_lock;				// lock utilizzato per modificare i campi presenti nella struct
	pthread_cond_t 	exit_cond;				// cond variable utilizzata per tutti i thread che devono uscire dal bus
	pthread_cond_t	enter_cond;				// cond variable utilizzata per tutti i thread che devono entrare nel bus
	pthread_cond_t 	journey_cond;			// cond variable utilizzata per fare partire il viaggio sul bus
} Bus;

Bus b;

////////////////////////////////////////////////////////////////////////////////////////////
/*	
 *	--PROBLEMA DEL BUS CON VARIABILI CONDIZIONALI (BARRIER LIKE)--
*/
////////////////////////////////////////////////////////////////////////////////////////////

int init_bus(Bus* b, unsigned int posti) {
	/*
	 * Funzione utilizzata per l'inizializzazione dei campi
	 * presenti all'interno della struct bus
	*/
	if(posti < 1) return 1;

	b->c_posti = posti;
	b->posti_presi = 0;
	b->up = 1;

	pthread_mutex_init(&b->bus_lock,     NULL);
	pthread_cond_init( &b->journey_cond, NULL);
	pthread_cond_init( &b->enter_cond,   NULL);
	pthread_cond_init( &b->exit_cond,    NULL);
	
	return 0;
}

void* life_bus() {
	/*
	 * Funzione utilizzata e fatta eseguire dal thread del bus.
	 * Principalmente il thread appena parte si ferma sulla variabile 
	 * condizionale journey_cond di bus e aspetta finchè non gli arriva un
	 * segnale su quest'ultima.
	 * Per motivi legati all'inizializzazione degli altri passeggeri, appena
	 * il thread prende il lock lo dovrà subito rilasciare in modo tale
	 * che gli altri passeggeri possano interagire con il bus per potersi 
	 * mettere in attesa dentro il while (guardare up_bus.
	 * Una volta finito il giro, dopo lo sleep di 3 secondi, il bus manda
	 * un segnale in broadcast a tutti i thread che sono fermi sulla variabile
	 * condizionale exit_cond per poi ricomniciare il tutto.
	*/
	for(int i = 0; i < NUMVIAGGI; i++) {
			pthread_cond_wait(&b.journey_cond, &b.bus_lock);
			pthread_mutex_unlock(&b.bus_lock);
			printf("/////////////////////////////////////////////////////////////////////////////////////////////////\n");
			printf("Viaggio %d del bus iniziato\n", i+1);
			sleep(2);
			printf("Viaggio %d del bus finito\n", i+1);
			printf("/////////////////////////////////////////////////////////////////////////////////////////////////\n");
			pthread_cond_broadcast(&b.exit_cond);
	}
}

void up_bus() {
	/*
	 * Funzione utilizzata dai thread passeggero per poter entrare dentro
	 * il bus e fare il viaggio.
	 * Appena entrati nella funzione devono aspettare di prendere il lock 
	 * del bus e una volta preso se il valore di up non è a true vanno
	 * in wait sulla variabile conzionale enter_cond.
	 * Una volta svegliati su b.ups sarà vero usciranno dal while incrementando 
	 * il numero di posti presi e se quel numero sarà uguale al numero di posti 
	 * sul bus allora si rimetterà il valore di b.up a false e si invierà un segnale
	 * al thread che è sulla variabile journey_cond (solo il bus).
	*/
	pthread_mutex_lock(&b.bus_lock);
		while(!b.up) {
		//		printf("Addormentato su b.enter_cond\n");
				pthread_cond_wait(&b.enter_cond, &b.bus_lock);
		}	

		printf("entrato\n");
		b.posti_presi++;

		if(b.posti_presi == b.c_posti) {
			b.up = 0;
			pthread_cond_signal(&b.journey_cond);
		}
		pthread_cond_wait(&b.exit_cond, &b.bus_lock);

	pthread_mutex_unlock(&b.bus_lock);
}

void down_bus() {
	/*
	 * Funzione utilizzata per gestire i thread passeggeri che sono sul bus e stanno viaggiando.
	 * Appena entrati nella funzione veongo messi in wait sulla variabile condizionale
	 * exit_cond. 
	 * Appena vengono svegliati decrementano posti_presi finche non arriva a 0.
	 * Una volta arrivato a 0 rimettono b.up a true e inviano un segnale ai thread che 
	 * stanno dormendo su enter_cond.
	*/
	pthread_mutex_lock(&b.bus_lock);
		b.posti_presi--;
		if(b.posti_presi == 0) {
			b.up = 1;
			pthread_cond_broadcast(&b.enter_cond);
		}
	pthread_mutex_unlock(&b.bus_lock);
}

void* life_pass() {
	/*
	 *	Utilizzata per simulare la vita del passeggero.
	 *	Lo sleep serve solo per far si che un passeggero una 
	 *	volta salito sul bus non tenti di risalrci subito 
	 *	così da mettere alla prova il codice.
	 *	Il risultato che bisogna aspettaresi e questo:
	 *	entrato
	 *	entrato
	 *	entrato
	 *	entrato
	 *	entrato
	 *	Viaggio del bus iniziato
	 *	Viaggio del bus finito
	 *	Con in mezzo degli: Addormentato su b.enter_cond
	*/
	for(int i = 0; i < NUMGIRI; i++) {
		up_bus();
		down_bus();
		sleep(3);
	}
}


int main(int argc, char** args) {
	pthread_t pass_th[NUMPASS], bus_th;
	init_bus(&b, NUMPOSTIBUS);

	printf("Numero Posti: %d\n", NUMPOSTIBUS);
	printf("Numero Passeggeri: %d\n", NUMPASS);
	printf("Numero Viaggi per Passegger0: %d\n", NUMGIRI);
	printf("Numero Viaggi Totali: %d\n", NUMVIAGGI);

	sleep(2);
	
	pthread_create(&bus_th, NULL, life_bus, NULL);
	for(int i = 0; i < NUMPASS; i++) {
		pthread_create(&pass_th[i], NULL, life_pass, NULL);
	}

	pthread_join(bus_th, NULL);
	for(int i = 0; i < NUMPASS; i++) {
		pthread_join(pass_th[i], NULL);
	}
}

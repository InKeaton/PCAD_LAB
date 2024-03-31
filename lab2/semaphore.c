#include "semaphore.h"

int my_sem_init(my_semaphore * ms, unsigned int v) {
	//Init V
	ms->V = v;
	//Init and control lock
	if(pthread_mutex_init(&ms->lock, NULL)) {
		return 1;
	}
	//Init and control cond_var
	if(pthread_cond_init(&ms->varcond, NULL)) {
		return 1;
	}
	return 0;
}

int my_sem_wait(my_semaphore* ms) {
	while(ms->V <= 0) {
		pthread_cond_wait(&ms->varcond,	&ms->lock);
	}
	ms->V--;
	return 0;
}

int my_sem_signal(my_semaphore* ms) {
	ms->V++;
	pthread_cond_signal(&ms->varcond);
	return 0;
}


int my_sem_destroy(my_semaphore* ms) {
	if(pthread_mutex_destroy(&ms->lock)) {
		return 1;
	}
	
	if(pthread_cond_destroy(&ms->varcond)) {
		return 1;
	}
	return 0;
}



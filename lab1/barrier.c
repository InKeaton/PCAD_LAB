#include "barrier.h"

unsigned int pthread_my_barrier_init(my_barrier* mb, unsigned int v) {
	if(v == 0) { return -1; }
	pthread_mutex_init(&mb->lock, NULL);
	mb->vinit = v;
	mb->val 	= 0;
	return 0;
}

unsigned int pthread_my_barrier_wait(my_barrier* mb) {
	pthread_mutex_lock(&mb->lock);	
		mb->val++;
		if(mb->val >= mb->vinit) { pthread_cond_broadcast(&mb->varcond); }
		while(mb->val < mb->vinit) {
			printf("Non siamo abbastanza :((, siamo .... : %d\n", mb->val);
			pthread_cond_wait(&mb->varcond, &mb->lock);
			sleep(2);
		}
	pthread_mutex_unlock(&mb->lock);	
	return 0;
}

my_barrier m;	

void* prova() {
	pthread_my_barrier_wait(&m);
	printf("Ewwiwa :)))\n");
}

int main() {
	pthread_t t1, t2, t3, t4, t5;
	pthread_my_barrier_init(&m, 5);

	pthread_create(&t1,NULL, prova, NULL);
	pthread_create(&t2,NULL, prova, NULL);
	pthread_create(&t3,NULL, prova, NULL);
	pthread_create(&t4,NULL, prova, NULL);
	pthread_create(&t5,NULL, prova, NULL);
	
	pthread_join(t1, NULL);
	pthread_join(t2, NULL);
	pthread_join(t3, NULL);
	pthread_join(t4, NULL);
	pthread_join(t5, NULL);

	return 1;
}

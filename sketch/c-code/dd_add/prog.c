#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct ListNode{
  int  value;
  struct ListNode*  next;
     struct ListNode*  previous;
};

struct List {
    int size;
  struct ListNode*  head;
};

void newList(struct List **_out) ;
void addFirst(struct List **l, int val);
void newNode(int v, struct ListNode **_out);

void newList(struct List** l) {
  *l = malloc( sizeof(struct List)  ); 
    struct ListNode* h;
    newNode(0,&h);
  (*l)->head = h;
    (*l)->head->next = (*l)->head;
    (*l)->head->previous = (*l)->head;
    (*l)->size = 0;
  return;
}

void newNode(int v, struct ListNode **n){
    *n = malloc(sizeof(struct ListNode));
    (*n)->value = v;
   (*n)->next = NULL;
     (*n)->previous = NULL;
}

void addFirst(struct List** l, int val) {

    struct ListNode* t = (*l)->head->next;
      struct ListNode* e ; 
  newNode(val, &e);
    (*l)->head->next = e;
    e->previous = (*l)->head;
    e->next = t;
    if (t != (*l)->head) 
        t->previous = e;
  (*l)->size = (*l)->size +1;
  return;
}


int main(int argc, char *argv[]) {
    if (argc<2) return 0;
    FILE *f = fopen(argv[1],"r");
    if (f==NULL) return 0;
    char x[20];
    struct List *l;
    newList(&l);
    
    while (fscanf(f,"%s",x)==1) {
        if (x[0]<='9'&& x[0]>='0')
            addFirst(&l,atoi(x));
    }
    ListNode* n = l->head;
    while ((n->next) != NULL) {
        printf("%d ", n->value);
        n = n->next;
    }
      while ((n->previous) != (l->head)) {
        printf("%d ", n->value);
        n = n->previous;
    }
    fclose(f);
    reverse(&l);
    return 0;
}

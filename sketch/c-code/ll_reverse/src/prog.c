#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct ListNode{
  int  value;
  struct ListNode*  next;
};

struct List {
    int size;
  struct ListNode*  head;
};
void newList(struct List **_out) ;
void reverse(struct List **l);
void addFirst(struct List **l, int val);
void newNode(int v, struct ListNode **_out);

void newList(struct List** l) {
  *l = malloc( sizeof(struct List)  ); 
  (*l)->head = NULL;
  return;
}

void newNode(int v, struct ListNode **n){
    *n = malloc(sizeof(struct ListNode));
    (*n)->value = v;
   (*n)->next = NULL;
}

void reverse(struct List** l) {
  if ((*l)->head == NULL) {
    return;
  }
  struct ListNode*  ln1 = (*l)->head;
  struct ListNode*  ln2 = (*l)->head->next;
  struct ListNode*  ln3 = NULL;
  struct ListNode*  ln4 = NULL;
  while (ln2 != NULL) {
    ln4 = ln2->next;
    ln1->next = ln3;
    ln3 = ln1;
    ln1 = ln2;
    ln2 = ln4;
  }
  (*l)->head = ln1;
  ln1->next = ln3;
} 

void addFirst(struct List** l, int val) {
  struct ListNode* n ; 
  newNode(val, &n);
  n->next = (*l)->head;
  (*l)->head = n;
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
    fclose(f);
    reverse(&l);
    struct ListNode* node = l->head;
    while (node!=NULL) {
         printf("%d ",node->value);
        node = node->next;
    }
    return 0;
}

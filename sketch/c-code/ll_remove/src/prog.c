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
void addFirst(struct List **l, int val);
void newNode(int v, struct ListNode **_out);
void remove(struct List** l, int val);

void newList(struct List** l) {
  *l = malloc( sizeof(struct List)  ); 
  (*l)->head = NULL;
    (*l)->size = 0;
  return;
}

void newNode(int v, struct ListNode **n){
    *n = malloc(sizeof(struct ListNode));
    (*n)->value = v;
   (*n)->next = NULL;
}

void  remove(struct List** l, int val,struct ListNode* del) {
    del = (*l)->head;
    if (del==NULL) {
        return;
    }
    if (del->value == val){
        (*l)->head = node->next;
        return;
    }
    struct ListNode* prev = (*l)->head;
   del = del->next;
    while (del!=NULL) {
        if (del->value==val) {
            prev->next= node->next;
            return;
        }
        prev  = del;
        del = del->next;
    }
    del = NULL;
}

void addFirst(struct List** l, int val) {
  struct ListNode* n ; 
  newNode(val, &n);
  n->next = (*l)->head;
  (*l)->head = n;
  (*l)->size = (*l)->size +1;
  return;
}


int main(int argc, char *argv[]) {
    if (argc<2) return 0;
//    FILE *f = fopen(argv[1],"r");
  //  if (f==NULL) return 0;
    struct List *l;
    newList(&l);
    char *x = argv[1];
    char* res;
    res = strtok(x," ");
    while (res !=NULL) {
           addFirst(&l,atoi(res));
        res = strtok(NULL," ");
    }
    //fclose(f);
    reverse(&l);
    struct ListNode* node = l->head;
    while (node!=NULL) {
         printf("%d ",node->value);
        node = node->next;
    }
    return 0;
}

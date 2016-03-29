#include <stdio.h>
#include <stdlib.h>
struct ListNode{
  int  value;
  struct ListNode*  next;
};

struct List {
    int size;
  struct ListNode*  head;
};
void newList(struct List *_out) ;
void reverse(struct List *l);
void addFirst(struct List *l, int val, struct ListNode *_out);
void newNode(int v, struct ListNode *_out);

void newList(struct List *_out_d_d) {
  _out_d_d = malloc( sizeof(struct List)  ); 
  return;
}

void reverse(struct List *l_e_e) {
  if ((l_e_e->head) == (NULL)) {
    return;
  }
  struct ListNode*  ln1_f_f=l_e_e->head;
    struct ListNode*  ln2_10_10=l_e_e->head->next;
    struct ListNode*  ln3_11_11=NULL;
    struct ListNode*  ln4_12_12=NULL;
  while ((ln2_10_10) != (NULL)) {
    ln4_12_12 = ln2_10_10->next;
    ln1_f_f->next = ln3_11_11;
    ln3_11_11 = ln1_f_f;
    ln1_f_f = ln2_10_10;
    ln2_10_10 = ln4_12_12;
  }
  l_e_e->head = ln1_f_f;
  ln1_f_f->next = ln3_11_11;
} 

void addFirst(struct List *l_13_13, int val_14_14, struct ListNode *_out_15_15) {
  struct ListNode  *n_s19_16_16;
  newNode(val_14_14, n_s19_16_16);
  _out_15_15 = n_s19_16_16;
  n_s19_16_16->next = l_13_13->head;
  l_13_13->head = n_s19_16_16;
  return;
}

void newNode(int v_17_17, struct ListNode *_out_18_18) {
  _out_18_18 = malloc( sizeof(struct ListNode)  ); ;
  _out_18_18->value = v_17_17;
     
  return;
}

int main(int argc, char *argv[]) {
   struct List  l_s1_1_1;
  newList(&l_s1_1_1);
    return 0;
}
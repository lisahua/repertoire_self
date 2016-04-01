#include <cstdio>
#include <assert.h>
#include <iostream>
using namespace std;
#include "vops.h"
#include "findBug_ll_reverse.h"
namespace ANONYMOUS{

ListNode* ListNode::create(int  value_, ListNode*  next_){
  void* temp= malloc( sizeof(ListNode)  ); 
  ListNode* rv = new (temp)ListNode();
  rv->value =  value_;
  rv->next =  next_;
  return rv;
}
List* List::create(ListNode*  head_){
  void* temp= malloc( sizeof(List)  ); 
  List* rv = new (temp)List();
  rv->head =  head_;
  return rv;
}
void reverseHarness__Wrapper() {
  reverseHarness();
}
void reverseHarness__WrapperNospec() {}
void reverseHarness() {
  List*  l_s1_1_1=NULL;
  newList(l_s1_1_1);
  reverse(l_s1_1_1);
  assert ((l_s1_1_1->head) == (NULL));;
  ListNode*  one_s3_3_3=NULL;
  addFirst(l_s1_1_1, 1, one_s3_3_3);
  reverse(l_s1_1_1);
  assert ((l_s1_1_1->head) == (one_s3_3_3));;
  assert ((one_s3_3_3->next) == (NULL));;
  List*  l_s5_4_4=NULL;
  newList(l_s5_4_4);
  ListNode*  one_s7_5_5=NULL;
  addFirst(l_s5_4_4, 1, one_s7_5_5);
  ListNode*  two_s9_7_7=NULL;
  addFirst(l_s5_4_4, 2, two_s9_7_7);
  assert ((l_s5_4_4->head) == (two_s9_7_7));;
  assert ((two_s9_7_7->next) == (one_s7_5_5));;
  assert ((one_s7_5_5->next) == (NULL));;
  reverse(l_s5_4_4);
  assert ((l_s5_4_4->head) == (one_s7_5_5));;
  assert ((one_s7_5_5->next) == (two_s9_7_7));;
  assert ((two_s9_7_7->next) == (NULL));;
  List*  l_s11_8_8=NULL;
  newList(l_s11_8_8);
  ListNode*  one_s13_9_9=NULL;
  addFirst(l_s11_8_8, 1, one_s13_9_9);
  ListNode*  two_s15_a_a=NULL;
  addFirst(l_s11_8_8, 2, two_s15_a_a);
  ListNode*  three_s17_c_c=NULL;
  addFirst(l_s11_8_8, 3, three_s17_c_c);
  reverse(l_s11_8_8);
  assert ((l_s11_8_8->head) == (one_s13_9_9));;
  assert ((one_s13_9_9->next) == (two_s15_a_a));;
  assert ((two_s15_a_a->next) == (three_s17_c_c));;
  assert ((three_s17_c_c->next) == (NULL));;
}
void newList(List*& _out_d_d) {
  _out_d_d = NULL;
  _out_d_d = List::create(NULL);
  _out_d_d->head = NULL;
  return;
}
void reverse(List* l_e_e) {
  if ((l_e_e->head) == (NULL)) {
    return;
  }
  ListNode*  ln1_f_f=l_e_e->head;
  ListNode*  ln2_10_10=l_e_e->head->next;
  ListNode*  ln3_11_11=NULL;
  ListNode*  ln4_12_12=NULL;
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
void addFirst(List* l_13_13, int val_14_14, ListNode*& _out_15_15) {
  _out_15_15 = NULL;
  ListNode*  n_s19_16_16=NULL;
  newNode(val_14_14, n_s19_16_16);
  _out_15_15 = n_s19_16_16;
  n_s19_16_16->next = l_13_13->head;
  l_13_13->head = n_s19_16_16;
  return;
}
void newNode(int v_17_17, ListNode*& _out_18_18) {
  _out_18_18 = NULL;
  _out_18_18 = ListNode::create(0, NULL);
  _out_18_18->value = v_17_17;
  _out_18_18->next = NULL;
  return;
}

}

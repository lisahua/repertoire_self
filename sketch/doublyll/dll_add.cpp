#include <cstdio>
#include <assert.h>
#include <iostream>
using namespace std;
#include "vops.h"
#include "dll_add.h"

namespace ANONYMOUS{

Entry* Entry::create(int  element_, Entry*  next_, Entry*  previous_){
  void* temp= malloc( sizeof(Entry)  ); 
  Entry* rv = new (temp)Entry();
  rv->element =  element_;
  rv->next =  next_;
  rv->previous =  previous_;
  return rv;
}
LinkedList* LinkedList::create(Entry*  header_, int  size_){
  void* temp= malloc( sizeof(LinkedList)  ); 
  LinkedList* rv = new (temp)LinkedList();
  rv->header =  header_;
  rv->size =  size_;
  return rv;
}
void addFrontHarness__Wrapper() {
  addFrontHarness();
}
void addFrontHarness__WrapperNospec() {}
void addLastHarness__Wrapper() {
  addLastHarness();
}
void addLastHarness__WrapperNospec() {}
void addFrontHarness() {
  LinkedList*  l_s2_1_1=NULL;
  newList(l_s2_1_1);
  assert ((l_s2_1_1->size) == (0));;
  assert ((l_s2_1_1->header->next) == (l_s2_1_1->header));;
  assert ((l_s2_1_1->header->previous) == (l_s2_1_1->header));;
  Entry*  one_s4_3_3=NULL;
  addFirst(l_s2_1_1, 8, one_s4_3_3);
  assert ((l_s2_1_1->header->next) == (one_s4_3_3));;
  assert ((l_s2_1_1->header->previous) == (l_s2_1_1->header));;
  assert ((one_s4_3_3->next) == (l_s2_1_1->header));;
  assert ((one_s4_3_3->previous) == (l_s2_1_1->header));;
   // assert ((l_s2_1_1->size) == (1));;
  static_assert ((l_s2_1_1->size) == (1), "45");;
  Entry*  two_s6_5_5=NULL;
  addFirst(l_s2_1_1, 5, two_s6_5_5);
  assert ((l_s2_1_1->header->next) == (two_s6_5_5));;
  assert ((l_s2_1_1->header->previous) == (l_s2_1_1->header));;
  assert ((two_s6_5_5->next) == (one_s4_3_3));;
  assert ((two_s6_5_5->previous) == (l_s2_1_1->header));;
  assert ((one_s4_3_3->next) == (l_s2_1_1->header));;
  assert ((one_s4_3_3->previous) == (two_s6_5_5));;
  assert ((l_s2_1_1->size) == (2));;
  Entry*  three_s8_7_7=NULL;
  addFirst(l_s2_1_1, 13, three_s8_7_7);
  assert ((l_s2_1_1->header->next) == (three_s8_7_7));;
  assert ((l_s2_1_1->header->previous) == (l_s2_1_1->header));;
  assert ((three_s8_7_7->next) == (two_s6_5_5));;
  assert ((three_s8_7_7->previous) == (l_s2_1_1->header));;
  assert ((two_s6_5_5->next) == (one_s4_3_3));;
  assert ((two_s6_5_5->previous) == (three_s8_7_7));;
  assert ((one_s4_3_3->next) == (l_s2_1_1->header));;
  assert ((one_s4_3_3->previous) == (two_s6_5_5));;
  assert ((l_s2_1_1->size) == (3));;
}
void addLastHarness() {
  LinkedList*  l_s14_9_9=NULL;
  newList(l_s14_9_9);
  assert ((l_s14_9_9->size) == (0));;
  assert ((l_s14_9_9->header->next) == (l_s14_9_9->header));;
  assert ((l_s14_9_9->header->previous) == (l_s14_9_9->header));;
  Entry*  one_s16_b_b=NULL;
  addLast(l_s14_9_9, 8, one_s16_b_b);
  assert ((l_s14_9_9->header->next) == (one_s16_b_b));;
  assert ((one_s16_b_b->next) == (l_s14_9_9->header));;
  assert ((one_s16_b_b->previous) == (l_s14_9_9->header));;
  assert ((l_s14_9_9->size) == (1));;
  Entry*  two_s18_d_d=NULL;
  addLast(l_s14_9_9, 5, two_s18_d_d);
  assert ((l_s14_9_9->header->next) == (one_s16_b_b));;
  assert ((l_s14_9_9->header->previous) == (l_s14_9_9->header));;
  assert ((one_s16_b_b->next) == (two_s18_d_d));;
  assert ((one_s16_b_b->previous) == (l_s14_9_9->header));;
  assert ((two_s18_d_d->next) == (l_s14_9_9->header));;
  assert ((two_s18_d_d->previous) == (one_s16_b_b));;
  assert ((l_s14_9_9->size) == (2));;
  Entry*  three_s20_f_f=NULL;
  addLast(l_s14_9_9, 13, three_s20_f_f);
  assert ((l_s14_9_9->header->next) == (one_s16_b_b));;
  assert ((l_s14_9_9->header->previous) == (l_s14_9_9->header));;
  assert ((one_s16_b_b->next) == (two_s18_d_d));;
  assert ((one_s16_b_b->previous) == (l_s14_9_9->header));;
  assert ((two_s18_d_d->next) == (three_s20_f_f));;
  assert ((two_s18_d_d->previous) == (one_s16_b_b));;
  assert ((three_s20_f_f->next) == (l_s14_9_9->header));;
  assert ((three_s20_f_f->previous) == (two_s18_d_d));;
  assert ((l_s14_9_9->size) == (3));;
}
void newList(LinkedList*& _out_10_10) {
  _out_10_10 = NULL;
  _out_10_10 = LinkedList::create(NULL, 0);
  Entry*  _out_s12_11_11=NULL;
  newEntry(_out_s12_11_11);
  _out_10_10->header = _out_s12_11_11;
  _out_10_10->header->next = _out_10_10->header;
  _out_10_10->header->previous = _out_10_10->header;
  _out_10_10->size = 0;
  return;
}
void addFirst(LinkedList* l_12_12, int o_13_13, Entry*& _out_14_14) {
  _out_14_14 = NULL;
  Entry*  t_15_15=l_12_12->header->next;
  Entry*  e_s10_16_16=NULL;
  newEntry(e_s10_16_16);
  _out_14_14 = e_s10_16_16;
  e_s10_16_16->element = o_13_13;
  l_12_12->header->next = e_s10_16_16;
  e_s10_16_16->previous = l_12_12->header;
  e_s10_16_16->next = t_15_15;
  if ((t_15_15) != (l_12_12->header)) {
    t_15_15->previous = e_s10_16_16;
  }
  //l_12_12->size = l_12_12->size + 1;
  return;
}
void addLast(LinkedList* l_17_17, int o_18_18, Entry*& _out_19_19) {
  _out_19_19 = NULL;
  Entry*  t_1a_1a=l_17_17->header->next;
  Entry*  e_s22_1b_1b=NULL;
  newEntry(e_s22_1b_1b);
  _out_19_19 = e_s22_1b_1b;
  e_s22_1b_1b->element = o_18_18;
  bool  __sa0_1c_1c=(t_1a_1a->next) != (l_17_17->header);
  while (__sa0_1c_1c) {
    t_1a_1a = t_1a_1a->next;
    __sa0_1c_1c = (t_1a_1a->next) != (l_17_17->header);
  }
  t_1a_1a->next = e_s22_1b_1b;
  e_s22_1b_1b->previous = t_1a_1a;
  e_s22_1b_1b->next = l_17_17->header;
  l_17_17->size = l_17_17->size + 1;
  return;
}
void newEntry(Entry*& _out_1d_1d) {
  _out_1d_1d = NULL;
  _out_1d_1d = Entry::create(0, NULL, NULL);
  _out_1d_1d->previous = NULL;
  _out_1d_1d->next = NULL;
  return;
}

}

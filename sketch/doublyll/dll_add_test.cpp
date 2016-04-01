#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <iostream>
#include "vops.h"
#include "dll_add.h"

using namespace std;

void addFrontHarness__Wrapper_ANONYMOUSTest(Parameters& _p_) {
  for(int _test_=0;_test_< _p_.niters ;_test_++) {
    try{
      ANONYMOUS::addFrontHarness__WrapperNospec();
      ANONYMOUS::addFrontHarness__Wrapper();
    }catch(AssumptionFailedException& afe){  }
  }
}

void addLastHarness__Wrapper_ANONYMOUSTest(Parameters& _p_) {
  for(int _test_=0;_test_< _p_.niters ;_test_++) {
    try{
      ANONYMOUS::addLastHarness__WrapperNospec();
      ANONYMOUS::addLastHarness__Wrapper();
    }catch(AssumptionFailedException& afe){  }
  }
}

int main(int argc, char** argv) {
  Parameters p(argc, argv);
  srand(time(0));
  addFrontHarness__Wrapper_ANONYMOUSTest(p);
  addLastHarness__Wrapper_ANONYMOUSTest(p);
  printf("Automated testing passed for dll_add\n");
  return 0;
}

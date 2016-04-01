#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <iostream>
#include "vops.h"
#include "findBug_ll_reverse.h"

using namespace std;

void reverseHarness__Wrapper_ANONYMOUSTest(Parameters& _p_) {
  for(int _test_=0;_test_< _p_.niters ;_test_++) {
    try{
      ANONYMOUS::reverseHarness__WrapperNospec();
      ANONYMOUS::reverseHarness__Wrapper();
    }catch(AssumptionFailedException& afe){  }
  }
}

int main(int argc, char** argv) {
  Parameters p(argc, argv);
  srand(time(0));
  reverseHarness__Wrapper_ANONYMOUSTest(p);
  printf("Automated testing passed for findBug_ll_reverse\n");
  return 0;
}

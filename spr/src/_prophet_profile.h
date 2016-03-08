#pragma once

#ifdef __cplusplus
extern "C" {
#endif

void __prof_init();

void __prof_track(unsigned long loc_idx);
/*        char* exp_srcf, int exp_l, int exp_c,
        char* spell_srcf, int spell_l, int sepll_c);*/

void __prof_exit();

#ifdef __cplusplus
}
#endif

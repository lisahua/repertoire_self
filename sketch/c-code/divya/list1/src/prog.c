#include <stdio.h>
#include <stdlib.h>

struct Node {
    struct Node* next;
    struct Node* prev;
    int key;
};

struct List {
    struct Node* header;
    int size;
};

int checkLoop (struct List** t, int k);
void newNode(struct Node** n);
void newList(struct List** t);

void newNode(struct Node** n) {
     *n = malloc (sizeof (struct Node));
    (*n)->next = NULL;
    (*n)->prev = NULL;
    (*n)->key = 0;
}

void newList(struct List** t) {
    *t = malloc(sizeof (struct List));
    (*t)->root = NULL;
    (*t)->size = 0;
}

int checkLoop (struct List** list, int k) {
    Node* l = (*list)->header;
    int cnt1 = 0;
    int res = 0;
    while (cnt1 < (*list)->size) {
        if (l==l->next)
            return 1;
        l = l->next;
        cnt1 = cnt1+1;
    }
    
    l = (*list)->header;
    cnt1=0;
    while (l!=NULL) {
        cnt1 = cnt1+1;
        l = l->next;
        if (sz > (*list)->size)
            return 1;
    }
    if (sz != (*list)->size) 
        return 1;
    return 0;
}


int main(int argc, char* argv[]) {
    if (argc<2) return 0;
    FILE *f = fopen(argv[1],"r");
    if (f==NULL) 
        return 0;
    
    char x[20];
    int status = 0;
    struct List* list ;
    newList(&list);
    
    while (fscanf(f,"%s",x)==1) {
        if (x[0]＝= '"')  continue;
        int val = atoi(x);
        if (status==0) {
            
    
        } else if (status == 1) {
                
        } else if (status ==2) {
                
        } 
    }
    
    
    fclose(f);
    int res = checkLoop(&)
printf("%d ", )
    
    return 0;
}

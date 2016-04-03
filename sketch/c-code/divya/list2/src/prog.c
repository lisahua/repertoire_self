#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct Node {
    struct Node* next;
    struct Node* prev;
    char* name;
};

struct List {
    struct Node* header;
    int size;
};

int repOK (struct List** t);
void newNode(struct Node** n);
void newList(struct List** t);
void insertNode(struct List** t,struct Node** n);
void findNode(struct List** t, char* name, struct Node** res);

void newNode(struct Node** n) {
     *n = malloc (sizeof (struct Node));
    (*n)->next = NULL;
    (*n)->prev = NULL;
    (*n)->name = NULL;
}

void newList(struct List** t) {
    *t = malloc(sizeof (struct List));
    (*t)->header = NULL;
    (*t)->size = 0;
}

void insertNode(struct List** t,struct Node** n) {
    struct Node* node = (*t)->header;
    (*t)->size = (*t)->size +1;
    if (node ==NULL) {
        (*t)->header = *n;
        return;
    }
    while ( node->next != NULL ) 
        node = node->next;
    node->next = *n;
 
    return;
}

void findNode(struct List** t, char* name, struct Node** res) {
    struct Node* node = (*t)->header;
    while (node != NULL) {
        if (strcmp(node->name, name)==0) {
            *res = node;
            return;
        }
        node = node->next;
    }
    
    if (node ==NULL) {
        newNode(&node);
        node->name = name;
        *res = node;
        insertNode(t,res);
        return;
    } 
}

int repOK (struct List** list) {
    struct Node* l = (*list)->header;
    int cnt1 = 0;
    int res = 0;
    int flag = 0; //set to 1 to test buggy version
    while (cnt1 < (*list)->size) {
        if (flag==1) {
            if (l==l->next)
                return 1;
        }
        l = l->next;
        cnt1 = cnt1+1;
    }
    
    l = (*list)->header;
    int sz=0;
    while (l!=NULL) {
        sz = sz+1;
        l = l->next;
        if (sz > (*list)->size)
            return 0;//inconsistent with origin
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
    
  struct Node* node=NULL;
    while (fscanf(f,"%s",x)==1) {
        if (x[0] == '"')  continue;
  
        struct Node* tmp=NULL;
        if (strcmp(x,"NULL")==0) 
            tmp = NULL;
        else   
            findNode(&list,x,&tmp);
       
        if (status==0) {
            node = tmp;
            status = status +1;
        }      
        else if (status == 1) {
            node->prev = tmp;
            status = status +1;
//            if (tmp==NULL) 
//                printf("%s","add prev NULL");
//            else 
//                printf("add prev %s", tmp->name);
        } 
        
        else if (status ==2) {
            node->next = tmp;
            status =0;
//            if (tmp==NULL) 
//                printf("%s","add next NULL ");
//            else 
//                printf("add next %s ", tmp->name);
        } 
    }
    
    fclose(f);
    int res = repOK(&list);
    printf("%d ", res);
    
    return 0;
}

#include <stdio.h>
#include <stdlib.h>

struct Node {
    struct Node* parent;
    struct Node* left;
    struct Node* right;
    int key;
};

struct Tree {
    struct Node* root;
    int size;
};

int insertNode (struct Tree** t, int k);
void newNode(struct Node** n);
void newTree(struct Tree** t);
void printTree(struct Node** r);


void newNode(struct Node** n) {
     *n = malloc (sizeof (struct Node));
    (*n)->parent = NULL;
    (*n)->left = NULL;
    (*n)->right = NULL;
    (*n)->key = 0;
}

void newTree(struct Tree** t) {
    *t = malloc(sizeof (struct Tree));
    (*t)->root = NULL;
    (*t)->size = 0;
}

int insertNode (struct Tree** t, int k) {
    struct Node* y;
    struct Node* x = (*t)->root;
    
    printf ("print ahead %s",",");
    printTree(&((*t)->root));
    
    while (x != NULL) {
        y = x;
        if (x->key==k) {
            return 1;
        }
        //BUG should be if not while
        //while ((x != NULL) && k<x->key) 
        if ((x != NULL) && k<x->key) 
            x = x->left;
        if ((x!= NULL)&& k>x->key)
            x = x->right;
    }
    
    newNode(&x);
    x->key = k;
    
    if (y==NULL)
        (*t)->root = x;
    else {
        if (k<y->key)
            y->left = x;
        else 
            y->right = x;
    }
    x->parent = y;
    (*t)->size = (*t)->size +1;
    
     printf ("print after %s",",");
    printTree(&((*t)->root));
    return 0;   
}

void printTree(struct Node** r) {
    if ((*r)==NULL) return;
    printf("%d ",(*r)->key);
    printTree(&((*r)->left));
     printTree(&((*r)->right));
}


int main(int argc, char* argv[]) {
    if (argc<2) return 0;
    FILE *f = fopen(argv[1],"r");
    if (f==NULL) 
        return 0;
    
    struct Tree* t;
    newTree(&t);
    
    char x[20];
    while (fscanf(f,"%s",x)==1) {
        if (x[0]!= '"')
            insertNode(&t,atoi(x));
    }
    fclose(f);
    
    struct Node* n = t->root;
    printTree(&n);
    
    return 0;
}

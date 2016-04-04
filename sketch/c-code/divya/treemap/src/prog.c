#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct Entry {
    struct Entry* parent;
    struct Entry* left;
    struct Entry* right;
    char* value;
    int color;
};

struct TreeMap {
    struct Entry* root;
    int size;
};

void newEntry(struct Entry** n);
void newTreeMap(struct TreeMap** t);
void getFirstEntry(struct TreeMap** tree, struct Entry** p);
void successor(struct Entry** t, struct Entry** p);
int containsValue(struct Entry* root, char* value);
void buildFromSorted (int level, int lo, int hi, int redLevel, int val_i, char* value[], struct Entry** res);
void buildTree(int size, char* value[], struct Entry** root) ;

void newEntry(struct Entry** n) {
     *n = malloc (sizeof (struct Entry));
    (*n)->parent = NULL;
    (*n)->left = NULL;
    (*n)->right = NULL;
    (*n)->color = 1;
    (*n)->value = NULL;
}

void newTreeMap(struct TreeMap** t) {
    *t = malloc(sizeof (struct TreeMap));
    (*t)->root = NULL;
    (*t)->size = 0;
}

void getFirstEntry(struct TreeMap** tree, struct Entry** p) {
    *p = (*tree)->root;
    if ((*p)!=NULL) {
        while ((*p)->left != NULL)
            *p = (*p)->left;
    }
    return ;
}

void successor(struct Entry** t, struct Entry** p) {
    if ((*t)==NULL) {
        (*p)=NULL;
        return;
    }else if ((*t)->left !=NULL) {
        *p = (*t)->right;
        while ((*p)->left != NULL)
            *p = (*p)->left;
        return;
    } else {
        *p = (*t)->parent;
        struct Entry* ch = *t;
        while (((*p) != NULL) && (ch ==(*p)->right)) {
            ch = *p;
            *p = (*p)->parent;
        }
        return;
    }   
}

int containsValue(struct Entry* root, char* value) {
    struct Entry* e = root;
    
    if (e !=NULL) {
        while (e->left != NULL)
            e = e->left;
    }
    
    int cnt = 0;
    while ((e!= NULL)&& (cnt<10)) {
        cnt++;
        if ((value ==NULL && e->value == NULL) || (strcmp(name, e->name)==0))
            return 0;
        
        int cond1 = (e->right != NULL);
        //BUG RBTERR4 comment out for correct version
      //  cond1 = ((e->right != NULL)&& (e->left != NULL));
        if (cond1==0) {
            struct Entry* p = e->right;
            while ((p->left != NULL)) 
                p = p->left;
            e = p;
        } else {
            struct Entry* ch = e;
            //BUG RBTERR3 comment out for correct version
           // ch = e->parent->right;
            
            struct Entry* p = e->parent;
            int cond = ((p!=NULL)&& (ch == p->right));
            //BUG RBTERR1 comment out for correct version
            //cond = ((p != NULL)&& (p->left ==NULL));
            //BUG RBERR2 comment out for correct version
            //cond = (p!=NULL);
            
            while (cond==0) {
                ch = p;
                p = p->parent;
                cond = ((p!=NULL) && (ch==p->right));
                 //BUG RBTERR1 comment out for correct version
                //cond = ((p != NULL)&& (p->left ==NULL));
                 //BUG RBERR2 comment out for correct version
                //cond = (p!=NULL);
            }
            e=p;  
        }
    }
    if (cnt==10)
        return 1;
}

void buildFromSorted (int level, int lo, int hi, int redLevel, int val_i, char* value[], struct Entry** res) {
    if (hi < lo) {
        *res = NULL;
        return;
    }
    int mid = (lo+hi)/2;
    Entry* left = NULL;
    if (lo < mid)
         buildFromSorted(level+1, lo, mid-1,redLevel, val_i, value, &left);
    
    struct Entry* middle;
    newEntry(&middle);
    middle->value = value[val_i];
    
    if (level==redLevel)
        middle->color = 0;
    if (left != NULL) {
        middle->left = left;
        left->parent = middle;
    }
    
    if (mid < hi) {
        struct Entry* right;
        buildFromSorted(level+1,mid+1,hi, redLevel,val_i+1, value, &right);
        middle->right = right;
        right->parent = middle;
    }
    *res = middle;  
}

void buildTree(int size, char* value[], struct Entry** root) {
    int level = 0;
    for (int m=size-1;m>=0;m=m/2-1)
        level++;
    
    buildFromSorted(0,0,size-1,level,0,value,root);
}


int main(int argc, char* argv[]) {
    if (argc<2) return 0;
    FILE *f = fopen(argv[1],"r");
    if (f==NULL) 
        return 0;
    
    struct TreeMap* t;
    newTreeMap(&t);
    
    char x[20];
    char* input[20];
    int i=0;
    char* find;
    fscanf(f,"%s",find);
    printf("find value %s,",find);
    while (fscanf(f,"%s",x)==1) {
        if (x[0]!= '"') {
           input[i] = x;
            i = i+1;
        }
    }
    fclose(f);
    printf("find value %d,",i);

    struct Entry* root = t->root;
    buildTree(i,input,&root);
    int res = containsValue(root, find);
    printf("output %d",res);
    
    return 0;
}

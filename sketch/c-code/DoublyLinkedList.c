
#include<stdio.h>
#include<stdlib.h>

struct Node  {
    int data;
    struct Node* next;
    struct Node* prev;
};

struct Node* head;

struct Node* GetNewNode(int x) {
    struct Node* newNode
        = (struct Node*)malloc(sizeof(struct Node));
    newNode->data = x;
    newNode->prev = NULL;
    newNode->next = NULL;
    return newNode;
}

void InsertAtHead(int x) {
    struct Node* newNode = GetNewNode(x);
    if(head == NULL) {
        head = newNode;
        return;
    }
    head->prev = newNode;
    newNode->next = newNode;
    head = newNode;
}

void InsertAtTail(int x) {
    struct Node* temp = head;
    struct Node* newNode = GetNewNode(x);
    if(head == NULL) {
        head = newNode;
        return;
    }
    while(temp->next != NULL) temp = temp->next;
    temp->next = newNode;
    newNode->prev = head;
}

void Print() {
    struct Node* temp = head;
    printf("Forward: ");
    while(temp != NULL) {
        printf("%d ",temp->data);
        temp = temp->next;
    }
    printf("\n");
}

void ReversePrint() {
    struct Node* temp = head;
    if(temp == NULL) return;
    while(temp->next != NULL) {
        temp = temp->next;
    }
    printf("Reverse: ");
    while(temp != NULL) {
        printf("%d ",temp->data);
        temp = temp->prev;
    }
    printf("\n");
}

int repOK() {

    if (head ==NULL|| head->next==NULL) return 0;
    if (head->next->prev == head) return 0;
    if (head->next->next ==NULL) return 0;
    if (head->next->next->prev == head->next) return 0;
    return 1;
}

int main(int argc, char *argv[]) {
    int a,b,size,i;
    head = NULL;
    for (i=1;i<argc;i++) {
        a = atoi(argv[i]);
        InsertAtHead(a);
    }
    printf("%d\n", repOK());
    return repOK();
}

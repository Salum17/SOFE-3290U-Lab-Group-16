#define _XOPEN_SOURCE 700 k
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>


void write_file(const char *filename, const char *content) {
    FILE *file = fopen(filename, "w");
    fprintf(file, "%s\n", content);
    fclose(file);
}

void read_file(const char *filename) {
    FILE *file = fopen(filename, "r");
    char buffer[256];
    fgets(buffer, sizeof(buffer), file);
    printf("%s: %s", filename, buffer);
    fclose(file);
}


int main(void){
    pid_t pid1, pid2;

    // Master process writes to files
    write_file("child1.txt", "child 1");
    write_file("child2.txt", "child 2");

    pid1 = fork();
    if (pid1 == 0){
        sleep(1);
        read_file("child1.txt");
        exit(0);
    }

    pid2 = fork();
    if (pid2 == 0){
        sleep(1);
        read_file("child2.txt");
        exit(0);
    }

    }

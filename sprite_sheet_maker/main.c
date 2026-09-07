#include <stdio.h>
#include <stdlib.h>

typedef unsigned char BOOL;

#define TRUE         (1)
#define FALSE        (0)
#define MAX_ARGS     (1)

typedef enum {
    COMMAND_NONE       = 0x0,
    COMMAND_COMPILE    = 0x1,
    COMMAND_RUN        = 0x2
} COMMAND_TYPE;

#define SUCCESS         (0)
#define ARG_COUNT_ERROR (1)
#define ARG_TYPE_ERROR  (2)
#define HELP_MSG        "Options: [c, r, cr]\nUsage: ./run_linux cr"

COMMAND_TYPE get_commands_from_string(const char* string);
int  get_string_size(const char* string);
char to_lower(char c);

int main(int argc, char *argv[]) {
    if ((argc <= 1) || (argc > (MAX_ARGS+1))) {
        printf("Expected %d arg(s)! Received: %d\n" HELP_MSG "\n", MAX_ARGS, (argc-1));
        return ARG_COUNT_ERROR;
    }

    COMMAND_TYPE commands = get_commands_from_string(argv[1]);
    if (commands == COMMAND_NONE) {
        printf("Could not find valid arguments!\n" HELP_MSG "\n");
        return ARG_TYPE_ERROR;
    }

    if (commands & COMMAND_COMPILE) {
        system("javac sprite/*.java");
    }

    if (commands & COMMAND_RUN) {
        system("java sprite.EntryPoint");
    }

    return SUCCESS;
}

COMMAND_TYPE get_commands_from_string(const char* string) {
    int index;
    int string_size = get_string_size(string);
    COMMAND_TYPE commands = COMMAND_NONE;

    for (index = 0; index < string_size; index++) {
        char lower_char = to_lower(string[index]);
        if (lower_char == 'c') {
            commands |= COMMAND_COMPILE;
        } else if (lower_char == 'r') {
            commands |= COMMAND_RUN;
        }
    }

    return commands;
}

int get_string_size(const char* string) {
    int index = 0;
    for (; string[index] != '\0'; index++) {}

    // Adding 1 to count the '\0'
    return (index + 1);
}

char to_lower(char c) {
    if (c >= 'A' && c <= 'Z') {
        return c - 32;
    }
    return c;
}

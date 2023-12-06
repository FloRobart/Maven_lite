---
date: September 2023
section: 1
title: Maven Lite
---

# NAME

mvnl - Lightweight and simple Java project manager inspired by the
`Maven` project manager.

# SYNOPSIS

mvnl \[OPTION\]\... \[ARGUMENT\]\...

# DESCRIPTION

Allows compiling and running a Java project using minimal options and
manipulations. Facilitates Java project compilation and execution more
simply than Maven.

All options take a single argument except for -c and -l, which take
none.

The order of options doesn\'t matter except for the \--file (-f) and
\--help (-h) options, which must be the first option on the command line
if used. Double quotes are not required.

Options used with -c while they are only usable with -l are ignored, and
vice versa. Options usable with -c or -l are also usable with -cl and
-lc.

Mandatory arguments for long options are also mandatory for short
options. For example, -s and \--source are equivalent.

# EXAMPLES

-   `mvnl -s src -o bin -c -e UTF-8` \--\> compiles the Java project
    with UTF-8 encoding located in the src directory and places the
    compiled files in the bin directory.

-   `mvnl -s src -o bin -m package.Main -cl` \--\> compiles and runs the
    Java project located in the src directory, puts the compiled files
    in the bin directory, and then runs the package.Main class.

-   `mvnl -s src -o bin -m package.Main -lc -arg argument_1 -arg argument_2`
    \--\> compiles and runs the Java project located in the src
    directory and puts the compiled files in the bin directory, then
    runs the package.Main class with argument_1 and argument_2 in the
    order they appear in the command.

-   `mvnl -m package.Main -l -cp bin:lib` \--\> runs the package.Main
    class while adding the bin and lib directories to the project\'s
    classpath. Please note that on Linux, the path separator is \':\'
    and not \';\'.

-   `mvnl -m Main -d lib -l` \--\> runs the Main class while adding all
    JAR files from the lib directory to the project\'s classpath.

-   `mvnl -s src -o bin -m package.Main -cl -dt data` \--\> compiles the
    Java project located in the src directory, places the compiled files
    in the bin directory, runs the package.Main class, and copies the
    data directory to the bin directory.

-   `mvnl -f config.txt -cl` \--\> loads options from the config.txt
    file. Contents of the config.txt file:
    `-s src -o bin -m package.Main`. Compiles and runs the Java project
    located in the src directory, puts the compiled files in the bin
    directory, and then runs the package.Main class.

# DEFAULT BEHAVIOR

By default, if no options are specified, the `mvnl` command displays the
help page, which is shown with the `-h` or `--help` option. This help
page is different and simpler than the manual page shown with the
`man mvnl` command.

# OPTIONS

## All Options

-v , \--version Display the version and exit.

-cr , \--create Create the project tree as well as a file
default config. If the output folder is not specified, the
Default folder is the current folder.

-s , \--source Root directory of the project to compile.

-o , \--output Directory for compiled files output.

-cp , \--classpath List of JAR files to add to the classpath during
compilation and execution. JAR files should be separated by \':\'. The
default classpath is the output directory of the compiled files if the
-o option is used, otherwise, the program will prompt you to specify the
classpath.

-d , \--dependency Directory containing JAR files used by the program.
All JAR files at the root of this directory will be added to the
classpath during compilation and execution.

-n , \--name Change the name of the file containing the paths of Java
files to compile. The default name is \`compile.list\'. Only usable with
the -c option.

-e , \--encoding Change the encoding of Java files to compile. The
default encoding is \`UTF-8\'. Only usable with the -c option.

-m , \--main Main class to run. Only usable with the -l option.

-dt , \--data Directory containing project data. Copies the data
directory to the output directory. Only usable with the -c option.

-arg , \--arguments Arguments to pass to the main class. One argument
per option, which means if you want to pass two arguments, you should
use the -arg option twice. The order of arguments passed to the main
class is the same as the order on the command. Command-line arguments
take precedence over arguments from the configuration file. Arguments
cannot contain spaces, or they will result in a bug. Only usable with
the -l option.

-f , \--file Configuration file. Loads options from a configuration
file, with space and newline separators. Options from the configuration
file take precedence over command-line options. The -f option must be
the first option on the command line.

-c , \--compilation Compile the project.

-l , \--launch Run the project.

-cl , \--compile-launch Compile and run the project. (Equivalent to -c
-l)

-lc , \--launch-compile Compile and run the project. (Equivalent to -l
-c)

-h , \--help Display help and exit.

## Mandatory options for compilation are:

-s , \--source Root directory of the project to compile.

-o , \--output Directory for compiled files output.

-c , \--compilation Compile the project.

## Mandatory options for running are:

-m , \--main Main class to run.

-l , \--launch Run the project.

-cp , \--classpath See the -cp option in the list of options above.

## Mandatory options for compilation and running are:

-s , \--source Root directory of the project to compile.

-o , \--output Directory for compiled files output.

-m , \--main Main class to run.

-cl , \--compile-launch Compile and run the project. (Equivalent to -c
-l)

# RETURN CODES

0: Everything went well.

1: An error occurred.

# FILES

Maven Lite consists of only 2 files.

-   `'mvnl'`, the main file located in the \'/usr/bin/\' directory.

-   `'mvnl.1.gz'`, the help file containing the manual page displayed
    with the `man mvnl` command, located in the
    `'/usr/local/man/fr/man1/'` directory.

# BUGS

There is a single known bug related to arguments with spaces, whether on
the command line or in the configuration file. Therefore, arguments
should not contain spaces.

# AUTHOR

Written by Robart Floris.

# BUG REPORTS

Report bugs via email to \<florisrobart.pro@gmail.com\> specifying what
the bug is, how I can reproduce it, and that it concerns Maven Lite version X.X.X.

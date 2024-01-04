#!/bin/bash

javac -d maven-lite_2.0-1_all/etc/maven-lite -encoding UTF-8 src/MavenLite.java && echo -e "\nCompilation terminée avec succès\n" || echo -e "\nErreur lors de la compilation\n"
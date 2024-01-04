#!/bin/bash

javac -d maven-lite_2.0-1_all/etc/maven-lite -encoding UTF-8 src/MavenLite.java && echo -e "\nCompilation terminée avec succès\n" || { echo -e "\nErreur lors de la compilation\n" ; exit 1; }
dpkg-deb --build maven-lite_2.0-1_all && (echo -e "\nPackage construit avec succès\n" ; sudo dpkg -i maven-lite_2.0-1_all.deb && echo -e "\nPackage installé avec succès\n" || echo -e "\nErreur lors de l'installation du package\n") || echo -e "\nErreur lors de la construction du package\n"
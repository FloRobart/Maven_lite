#!/bin/bash

debName="maven-lite_english_2.0-1_all" # maven-lite_french_2.0-1_all

javac -d ${debName}/etc/maven-lite -encoding UTF-8 src/en/MavenLite.java && echo -e "\nCompilation terminée avec succès\n" || { echo -e "\nErreur lors de la compilation\n" ; exit 1; }
# dpkg-deb --build ${debName} && (echo -e "\nPackage construit avec succès\n" ; sudo dpkg -i ${debName}.deb && echo -e "\nPackage installé avec succès\n" || echo -e "\nErreur lors de l'installation du package\n") || echo -e "\nErreur lors de la construction du package\n"
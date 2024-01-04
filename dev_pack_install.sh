#!/bin/bash

dpkg-deb --build maven-lite_2.0-1_all && (echo -e "\nPackage construit avec succès\n" ; sudo dpkg -i maven-lite_2.0-1_all.deb && echo -e "\nPackage installé avec succès\n" || echo -e "\nErreur lors de l'installation du package\n") || echo -e "\nErreur lors de la construction du package\n"
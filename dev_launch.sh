#!/bin/bash

for i in $@; do
    # Si l'argument commence par un backslash, on le double
    if [[ $i =~ ^\\ ]]; then
        args="$args \\$i"
    else
        args="$args $i"
    fi
done

java -cp maven-lite_2.0-1_all/etc/maven-lite MavenLite $@ && echo -e "\nMaven lite terminé avec succès\n" || echo -e "\nErreur lors de l'exécution de maven lite\n"
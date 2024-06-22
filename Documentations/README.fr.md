# Maven Lite

- See the [English documentation](./README.en.md)
- Voir la [documentation en PDF](./pdf/README.fr.pdf)

- Version : 2.1.0
- Auteur : Floris Robart

## Table des matières

- [Maven Lite](#maven-lite)
  - [Table des matières](#table-des-matières)
  - [Compatibilité](#compatibilité)
  - [Description](#description)
  - [Installation de Maven Lite et ses dépendances](#installation-de-maven-lite-et-ses-dépendances)
  - [Utilisation de Maven Lite](#utilisation-de-maven-lite)

## Compatibilité

- Windows 10 (Non testé)
- Windows 11 (Testé sur Windows 11 22H2)
- Linux possèdant `bash` (Testé sur Debian, Ubuntu 23.04, 23.10 et 24.04)
- MacOS Mojave 10.14.6 et inférieur (Non testé)
- MacOS Catalina 10.15 et supérieur à condition d'installé `bash` (Non testé)

## Description

Maven Lite est un système de gestion de projet java qui permet de compiler et lancer des projets java très simplement et rapidement.

Le but est de pouvoir compiler et/ou lancer un projet java en une seule courte commande, comme maven mais en plus simple.

Maven Lite est plus simple que Maven car il ne gère pas les dépendances hors local, les plugins, les phases de build, les déployements automatique, etc. Il est donc adapté pour les projets simples qui ne nécessitent pas de gérer des dépendances externes, cependant Maven Lite gère les dépendance local sous forme de fichier jar. Pour des projets plus complexes, il est conseillé d'utiliser Maven.

Maven lite permet de compiler le projet dans un dossier target, gèrer les dépendances locales (fichier jar) et éxecuter le projet.

L'intéret principale de maven lite dans la gestion des dépendances est de fournir un dossier qui contient toutes les dépendances, le script ajoutera automatiquement toutes les dépendances dans le classpath.

Il est possible que cette deuxième version de Maven Lite contienne des bugs. Si vous en trouvez, n'hésitez pas à les signaler.

Un fichier de configuration est disponible pour configurer les options de Maven Lite. Il est possible de se passer de fichier configuration et de mettre les options dans la ligne de commande mais c'est moins pratique.

L'ordre des options n'a pas d'importance.

Chaque option peut être utilisée plusieurs fois.

Chaque argument doit être précédé de son option, vous ne pouvez pas mettre d'argument sans option ni mettre toutes les options au début puis tous les arguments à la fin. Par exemple, `mvnl --source src/main/java --target target` est valide mais `mvnl --source --target src/main/java target` ne l'est pas.

**Il est recommandé de laisser les options par défaut parce que Maven Lite est basé sur les conventions simplifier de Maven qui sont des conventions de nommage et d'arborescence connues de beaucoup de développeurs java.**

## Installation de Maven Lite et ses dépendances

[Voir la documentation sur l'installation de Maven Lite](./Installation.fr.md)

## Utilisation de Maven Lite

[Voir la documentation sur l'utilisation de Maven Lite](./Utilisation.fr.md)

<a href="https://florobart.github.io/Maven_lite"><button type="button">Retour</button></a>

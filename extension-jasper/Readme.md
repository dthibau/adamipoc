Projet Extension Jasper via Scriptlet
-------------------------------------

Ce projet démontre l'utilisation d'un scriptlet dans un rapport.

Build du projet demo : ./mvnw -Pdemo clean package
(Compilation Java + compilation Rapports Jasper + packaging jar complet)


java -jar target/extension-jasper-0.0.1-SNAPSHOT-jar-with-dependencies.jar ./src/main/resources/Scriptlet.jrxml



Un rapport affichant le temps de génération doit s'afficher.


Le rapport utilise un scriptlet "fr.adami.ComputeTime" qui offre une méthode getComputeTime() renvoyant le nombre de mili-secondes écoulés depuis le début de la génération.
Cette méthode est utilisée dans un champ texte du rapport. 

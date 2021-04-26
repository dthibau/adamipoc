- Poc Jasper/Adami

Build :
- ./mvnw package 

Exécution :
java -jar target/pocjasper-<version>.jar --app.report-folder=<report's directory>

Le poc parcourt régulièrement le répertoire ${app.report-folder} et tente de compiler les nouveaux fichiers et les fichiers modifiés
Le résultat de la compilation ainsi que la date de modification des fichiers est mis en cache (HashMap)

Il offre un point d'accès REST permettant de générer un PDF

API Rest : voir swagger http://localhost:8080/swagger-ui.html
(le paramètre format n'est pas utilisé)

Modèle de données : Base Hsql générée automatiquement et initialisé avec src/main/resources/import.sql
Profil postres testé avec base locale postgres


Exemples du Répertoire de publication Jasper :  
- Rapport Simple : curl -X POST "http://localhost:8080/api/reports" -H  "Content-Type: application/json" -d "{\"name\":\"Simple\",\"format\":\"pdf\"}"> result.pdf

- Rapport avec Paramètres : 
curl -X POST "http://localhost:8080/api/reports" -H  "Content-Type: application/json" -d "{\"name\":\"Params\",\"params\":{\"String\":\"Une belle String\",\"Integer\":1},\"format\":\"pdf\"}" > result2.pdf

- Rapport avec Ressources
Le rapport Ressources références plusieurs ressources externes au fichier jrxml :
- Un fichier de style
- Des images au format (svg, png, jpg)
- Une police spécifique Gentium
- Un sous-rapport

**Toutes les référénces à ces fichiers indiquées dans le .jrxml sont relatives**

Pas de souci pour le fichier de style
Pour le format SVG nécessite la librairie Batik
Pour l'utilisation d'une police spécifique, la police Gentium a été packagée à partir de JasperStudio. (Voir https://community.jaspersoft.com/wiki/custom-font-font-extension#Import_the_Font_Extension) puis mis sous forme de jar dans le classpath (Gentium.jar)
Attention dans jas bien s'assurer que la propriété :
net.sf.jasperreports.extension.simple.font.families.**Gentium**=lobstertwo/fonts.xml correspond au nom utilisé dans le jrxml
Pas de souci pour le sous-rapport ....





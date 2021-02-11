- Poc Jasper/Adami

Le poc parcourt régulièrement le répertoire ${app.report-folder} et tente de compiler les nouveaux fichiers et les fichiers modifiés
Le résultat de la compilation ainsi que la date de modification des fichiers est mis en cache (HashMap)

Il offre un point d'accès REST permettant de générer un PDF

API Rest : voir swagger http://localhost:8080/swagger-ui.html
(le paramètre format n'est pas utilisé)

Modèle de données : Base Hsql générée automatiquement et initialisé avec src/main/resources/import.sql


Exemples du Répertoire de publication Jasper :  
- Rapport Simple : curl -X POST "http://localhost:8080/api/reports" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Simple\",\"format\":\"pdf\"}"> result.pdf

- Rapport avec Paramètres : 
curl -X POST "http://localhost:8080/api/reports" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Params\",\"params\":{\"String\":\"Une belle String\",\"Integer\":1},\"format\":\"pdf\"}" > result2.pdf

- Rapport avec Ressources


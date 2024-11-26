# Variables pour les répertoires source et sortie
SRC_DIR := src
OUT_DIR := out

# Obtenir tous les fichiers .java dans le répertoire source
SRC_FILES := $(wildcard *.java)

# Cible principale : compile et exécute le programme
all: clean compile

# Cible pour nettoyer les fichiers .class dans le dossier de sortie
clean:
	@echo "Suppression des fichiers .class dans $(OUT_DIR)"
	rm -rf $(OUT_DIR)/*.class

# Cible pour créer le dossier de sortie et compiler les fichiers Java
compile: $(OUT_DIR)
	@echo "Compilation des fichiers Java..."
	javac -d $(OUT_DIR) $(SRC_FILES)

# Cible pour exécuter le programme, en supposant que le point d'entrée est Main
run: all
	@echo "Exécution du programme..."
	java -cp $(OUT_DIR) Main.java

# Crée le répertoire de sortie s'il n'existe pas
$(OUT_DIR):
	mkdir -p $(OUT_DIR)

# Nettoie tous les fichiers générés
clean-all: clean
	rm -rf $(OUT_DIR)
	@echo "Nettoyage complet terminé."
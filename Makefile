# Indiquer le chemin des sources
SRC_DIR = src
OUT_DIR = out

# Obtenir les fichiers .java à partir du répertoire source et inclure Main.java
SRC_FILES := $(wildcard $(SRC_DIR)/*.java)
MAIN_FILE := Main.java

# Cible pour créer le dossier de sortie
$(OUT_DIR):
	mkdir -p $(OUT_DIR)

# Cible pour compiler toutes les classes
compile: $(OUT_DIR)
	javac -d $(OUT_DIR) $(SRC_FILES) $(MAIN_FILE)

# Cible pour exécuter le programme
run: compile
	java -cp $(OUT_DIR) Main

# Cible pour nettoyer les fichiers compilés
clean:
	rm -rf $(OUT_DIR)

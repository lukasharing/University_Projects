# ****** Compilación de módulos **********

.PHONY: all clean mrproper

all: problema2.pdf clean

./problema2.pdf: ./problema2.tex
		latexmk -pdf -pdflatex="pdflatex -interaction=nonstopmode" -use-make $<

# ************ Limpieza ************

clean: clean-img
	-@rm -f ./*.aux ./*.synctex.gz ./*.log ./*.fls ./*.fdb_latexmk ./*.dvi ./*.nav ./*.out ./*.snm ./*.toc
	@echo "Limpiando..."

clean-img:
	-@rm -f ./img/*.aux ./img/*.synctex.gz ./img/*.log ./img/*.fls ./img/*.fdb_latexmk ./img/*.dvi ./img/*.nav ./img/*.out ./img/*.snm ./img/*.toc

mrproper: clean
	-@rm -f ./*.pdf ./img/*.pdf
	@echo "Limpieza completada."

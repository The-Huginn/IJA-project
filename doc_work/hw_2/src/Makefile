FILE=requirements
all: pdf

pdf: clean
	pdflatex $(FILE).tex
	mv $(FILE).pdf ..

clean:
	rm -f *.aux
	rm -f *.log
	rm -f ../*.pdf
## Getting Started

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

## Creating jar file and exe

- Go to bin directory
- jar cfm vcstool.jar manifest.txt vcstool
- jpackage --name vcstool --input . --main-jar vcstool.jar --main-class vcstool.Main --win-console

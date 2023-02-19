# Project Title
Version Control And Automation System

# Project Description
The Automated Version Control System is a software tool that automates the process of version control, helping software developers manage changes made to source code.
The system leverages a reference algorithm with five key features: Init, Add, Commit, Branches, and Automate. 

* The Init feature creates a new .vcs directory that acts as a space for backup, creates the first commit object with a message as an initial commit, 
and creates a master branch by default. 
* The Add feature is used to stage files, marking the files in the current working version that are different from the current commit. 
* The Commit feature is used to create a snapshot of the present commit with the files in the staging area. 
* The push feature is used to push the versions directory to aws s3 bucket
* Finally the deploy feature is used to create a new image from dockerfile and then run that image

# Prerequisites 
* Install aws cli, and setup aws configuration
* Install java
* Install jpackage

# Installation
1. Go to bin directory

2. Run the following command: 
`jar cfm vcstool.jar manifest.txt vcstool`

3. Run the following command:
`jpackage --name vcstool --input . --main-jar vcstool.jar --main-class vcstool.Main --win-console`

4. Install the exe generated and add it to the path

# Commands
* `vcstool init`
* `vcstool commit <message>`
* `vcstool add <filename>`
* `vcstool log`
* `vcstool push <awsbucket name>`
* `vcstool deploy`


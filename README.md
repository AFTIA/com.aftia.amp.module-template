![Build Status](https://github.com/AFTIA/com.aftia.vm.system.module-template/workflows/Build%20and%20Test/badge.svg) 
# com.aftia.vm.system.module-template

A module template repository including a `core` and `ui` module for the development of new OSGI/Karaf apps.

## Prerequisites 

* [Maven 3.3.x](https://maven.apache.org/download.cgi)
* [Java 8 or 11](https://adoptopenjdk.net/)
* [NodeJS **LTS**](https://nodejs.org/en)
* Integrated Development Environment (IDE)
    * *(Recommended)* [Visual Studio Code](https://code.visualstudio.com/)
        * (Recommended Extension) [GIT Graph](https://marketplace.visualstudio.com/items?itemName=mhutchie.git-graph)
        * (Recommended Extension) [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
* [GIT SCM](https://git-scm.com/)

## Modules

The main parts of the template are:

* `core`: Java bundle containing all core functionality like OSGi services, listeners or schedulers, as well as component-related Java code such as servlets or request filters. This is where the server side business logic is stored. It can be used to expose HTTP(s) based requests to process data and perform operations in order to achieve any business request.
* `ui`: Java bundle containing all user interface and browser assets i.e. HTML, JS, CSS. This is where the fontend module binary is placed during a build (`target/webapp`) and then wrapped into an OSGi bundle in order to be deployed into the runtime.
* `frontend`: Maven + Node JS project used to develop, build, and assemble `ui` assets. This is where the front end business logic is stored. It leverages CSS (Tailwind) and Vue.js (SPA framework) to define beautiful web experiences which aim to satisfy business requirements. 

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

To build individual modules run in the project module directory the following command with Maven 3:

    mvn clean install

To install all dependencies for `frontend` development, run in the `module-template.frontend` directory the following command with Node + NPM:

    npm install

To run a live reload development environment during `frontend` development, run in the `module-template.frontend` directory the following command with Node + NPM:

    npm run serve

To rebuild and purge Tailwind CSS files, run in the `module-template.frontend` directory the following command with Node + NPM:

    npm run build:tailwind
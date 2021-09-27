![Build Status](https://github.com/AFTIA/com.aftia.vm.system.module-template/workflows/Build%20and%20Test/badge.svg) 
# com.aftia.vm.system.module-template

A module template repository including a `core` and `ui` module for the development of new OSGI/Karaf apps.

# How to build

In order to build the project run the following command from the root of your project directory `mvn clean install`. 

**Note:** By navigating into the `module-template.frontend/` and executing `npm install` && `npm run serve` the frontend application can be used

## core

This is where the server side business logic is stored. It can be used to expose HTTP(s) based requests to process data and perform operations in order to achieve any business request.

## frontend

This is where the front end business logic is stored. It leverages CSS (Tailwind) and Vue.js (SPA framework) to define beautiful web experiences which aim to satisfy business requirements. 

## ui

This is where the fontend module binary is placed during a build (`target/webapp`) and then wrapped into an OSGi bundle in order to be deployed into the runtime.
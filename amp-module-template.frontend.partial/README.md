# aftia-amp-module-template.frontend.partial

This partial project is used as a submodule to be injected in a parent project. On it's own, it does not require a build process since it will be handled by the parent project.

## Getting started

To start using this project as a submodule, first clone this project as a [Git submodule](https://git-scm.com/book/en/v2/Git-Tools-Submodules) within the parent application and run `npm run link` in the current directory. This will link the parent's `node_modules` directory to the current directory, allowing components to resolve dependencies.
#Molecular Science (MolSci)

The Molecular Science mod for Minecraft. This mod aims to add mechanics, machines, and items based on the amazing properties that things aquire when they get very small. These mechanics open the way to exciting possiblilities, such as matter conversion, energy production, and item translocation (teleportation), just to name a few.

##Development
###Contributing
If you wish to contribute to Molecular Science, feel free to fork the repo and make the changes you desire. When submitting a pull request, just be sure to include, in detail, what you changed/added. As is with all code, there is likely to be bugs crawling out of every little crack they can find, so we appreciate any bugfixing that is done.

###Building from source
As usual, if you want to build MolSci from scratch, you can either use an existing install of Gradle, or use the wrapper that comes with the repo. The steps to build are:
* Setup: Run gradle in the repository root: `gradlew[.bat] [setupDevWorkspace|setupDecompWorkspace] [eclipse|idea]`.
* Build: Run gradle in the repository root: `gradlew[.bat] build'.
* If you are getting random errors from Gradle, try running 'gradlew clean' and 'gradlew cleanCache'.

If you want to make changes to the mod, it helps to use `setupDecompWorkspace`, but you can still edit the mod and make changes to it if you only use `setupDevWorkspace`.

##Reporting Issues
If you are reporting issues, it helps to give us as much information as possible to help us diagnose the problem faster and more accurately.

When reporting an issue, please include to following information:
* Minecraft Version
* MolSci version
* Forge version (if you know it)
* Any mods that you might think are related to the error
* If possible, screenshots of the error
* If the error is a crash:
  * Steps to reproduce the crash
  * ForgeModLoader-client-0.log and Minecraft's crash report (both can be found in the base folder or the crash-reports folder in your minecraft installation directory).
  
*Important*: If you have Optifine installed, uninstall it and try again. Optifine does not play nice with Forge. At all. If you submit a bug report with Optifine installed, we will (probably) politely remind you to uninstall it, and then ignore the report until you submit one without Optifine.

##License
Most code in this mod is public domain under [Creative Commons 0](http://creativecommons.org/publicdomain/zero/1.0/).

Textures and other bianary files are licensed under [Creative Commons 3](http://creativecommons.org/licenses/by/3.0/).

Details of these licenses can be found in the [LICENSE](https://github.com/ColoradoCoders/MolSci/blob/master/LICENSE) document.

Under the above licenses, you are freely allowed to use this mod in any modpack you wish. However, we do not officially support any modpack and anyone who uses this mod in a modpack must take **full** responsibility for user support queries.

Feel free to contact the authors for any questions related to licensing.

####Authors
* defiant810 - code, primary author
* iLaptop - code
* Bunzlin8tr - textures

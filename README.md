# **CalcPoly** #

### App summary ###

* Plot P(x) = c3 * x^3 + c2 * x^2 + c1 * x + c0
* Use for calculations class written in C++
* To set coefficient values C0-C3 callback mechanism is used
* Max X value and step could be set in settings

### Screenshots ###

![Main screen](http://i.imgur.com/kMk1kZcm.png "Main screen")
![Settings screen](http://i.imgur.com/eriI5wim.png "Settings screen")
![Settings screen max value](http://i.imgur.com/ndLblw2m.png "Settings screen max value")
![Settings screen step](http://i.imgur.com/n6E1rASm.png "Settings screen step")
![Plot screen](http://i.imgur.com/Cs56EK2m.png "Plot screen")
![Plot screen point](http://i.imgur.com/dIG2GTlm.png "Plot screen point")

### Used third-party libraries ###

* [Butter Knife](https://github.com/JakeWharton/butterknife) for injections
* [Graph View](http://www.android-graphview.org/) for graph plotting

### Notes ###

* To build *.so files, run `./gradlew runSwig` in Terminal
* Latest [SWIG](http://www.swig.org/) and [NDK](https://developer.android.com/ndk/downloads/index.html) should be installed
* Working app. [gif](http://i.imgur.com/j9iwulF.gif)
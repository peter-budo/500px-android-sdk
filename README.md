500px-android-sdk
=================


Installation
------------

- clone the repository  
    <pre>git clone git://github.com/500px/500px-android-sdk</pre>

- add it as a dependency in your main module gradle file dependencies
    <pre>compile project(':500px-android-sdk')</pre>

- include module in settings.gradle
    <pre>include ':moduleA', ':500px-android-sdk'</pre>

OR

1. Build project which will produce <strong>500px-android-sdk.aar</strong> file
2. Copy <strong>aar</strong> to your <strong>lib</strong> folder
3. In your <strong>build.gradle</strong> new dependency inside libs folder as
    <pre>
    repositories {
        flatDir {
            dirs 'libs'
        }
    }
    </pre>
4. Add <strong>aar</strong> as project dependency
    <pre>compile (name:'500px-android-sdk', ext:'aar')</pre>

Usage
-----
Here is an [login activity](https://gist.github.com/3625085) sample.

Examples
--------
[TroubledPixels](https://github.com/arthurnn/TroubledPixels)

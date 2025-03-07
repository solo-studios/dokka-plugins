[//]: # (@formatter:off)
You can add the ${project.module} dokka plugin to your project with the following:

<div class="tabbed-set tabbed-alternate" data-tabs="1:3">
<input id="__tabbed_1_1" name="__tabbed_1" type="radio"/>
<input checked="checked" id="__tabbed_1_2" name="__tabbed_1" type="radio"/>
<input id="__tabbed_1_3" name="__tabbed_1" type="radio"/>
<div class="tabbed-labels">
<label for="__tabbed_1_1">Gradle Groovy</label>
<label for="__tabbed_1_2">Gradle Kotlin</label>
<label for="__tabbed_1_3">Gradle Version Catalog</label>
</div>
<div class="tabbed-content">
<div class="tabbed-block">

Add this to your `build.gradle`:
```groovy
dependencies {
    dokkaHtmlPlugin '${project.group}:${project.module}:${project.version}'
}
```

</div>
<div class="tabbed-block">

Add this to your `build.gradle.kts`:
```kotlin
dependencies {
    dokkaHtmlPlugin("${project.group}:${project.module}:${project.version}")
}
```

</div>
<div class="tabbed-block">

Add this to your `gradle/libs.versions.toml`:
```toml
[versions]
${project.module} = "${project.version}"

[libraries]
${project.module} = { group = "${project.group}", name = "${project.module}", version.ref = "${project.module}" }
```
Add this to your `build.gradle`/`build.gradle.kts`:
```kotlin
dependencies {
    dokkaHtmlPlugin(libs.plugins.${project.module.replace("-", ".")})
}
```

</div>
</div>
</div>
<br>

To use a snapshot version, add the solo-studios snapshots repository to your `settings.gradle`/`settings.gradle.kts`

```kotlin
pluginManagement {
    repositories {
        maven("https://maven.solo-studios.ca/snapshots/") {
            name = "Solo Studios"
        }
    }
}
```

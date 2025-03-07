# Module Dokka Script Plugin

Include custom `.js` scripts, similar to how it is done with css styles and images.

### Including

{{dependencies}}

### Usage

The plugin is used as follows:

```kotlin
tasks {
    withType<AbstractDokkaTask>().configureEach {
        // type-safe plugin configuration
        pluginConfiguration<DokkaScriptsPlugin, DokkaScriptsConfiguration> {
            scripts = listOf(file("somescript.js")) // List<File>
            remoteScripts = listOf("https://example.com/somescript.js") // List<String>
        }
        // json plugin configuration
        val dokkaTweaksConfiguration = """
        {
            "scripts": ["somescript.js"],
            "remoteScripts": ["https://example.com/somescript.js"]
        }
        """
        pluginConfiguration.set(
            mapOf(
                "ca.solostudios.dokkastyles.plugin.DokkaStyleTweaksPlugin" to dokkaTweaksConfiguration,
            )
        )
    }
}
```

The scripts are included on every single page.

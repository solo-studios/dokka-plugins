/*
 * Copyright (c) 2023-2023 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file StyleTweaksInstaller.kt is part of dokka-plugins
 * Last modified on 16-09-2023 04:40 p.m.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * KT-FUZZY IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ca.solostudios.dokkastyles.plugin

import freemarker.cache.ClassTemplateLoader
import freemarker.template.Configuration
import freemarker.template.TemplateExceptionHandler
import org.jetbrains.dokka.pages.RendererSpecificResourcePage
import org.jetbrains.dokka.pages.RenderingStrategy
import org.jetbrains.dokka.pages.RootPageNode
import org.jetbrains.dokka.plugability.DokkaContext
import org.jetbrains.dokka.plugability.configuration
import org.jetbrains.dokka.transformers.pages.PageTransformer
import java.io.StringWriter


class StyleTweaksInstaller(private val context: DokkaContext) : PageTransformer {
    private val configuration = configuration<DokkaStyleTweaksPlugin, DokkaStyleTweaksConfiguration>(context)
    private val templaterConfiguration = Configuration(Configuration.VERSION_2_3_32).apply { configureTemplateEngine() }

    private fun Configuration.configureTemplateEngine() {
        templateLoader = ClassTemplateLoader(javaClass, "/tweaks/templates")

        unsetLocale()
        defaultEncoding = "UTF-8"
        templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
        logTemplateExceptions = false
        wrapUncheckedExceptions = true
        fallbackOnNullLoopVariable = false
        templateUpdateDelayMilliseconds = Long.MAX_VALUE
    }

    override fun invoke(input: RootPageNode): RootPageNode {
        val styleTweaksText = renderStyleTweaks(configuration)
        val styleTweaks = RendererSpecificResourcePage(
            "styles/tweaks.css",
            emptyList(),
            RenderingStrategy.Write(styleTweaksText),
        )

        return input.let { root ->
            if (context.configuration.delayTemplateSubstitution)
                root
            else
                root.modified(children = input.children + styleTweaks)
        }.transformContentPagesTree {
            it.modified(
                embeddedResources = it.embeddedResources + "styles/tweaks.css"
            )
        }
    }

    private fun renderStyleTweaks(configuration: DokkaStyleTweaksConfiguration?): String {
        val template = templaterConfiguration.getTemplate("tweaks.css.ftl")

        val model = mapOf(
            "styles" to (configuration ?: DokkaStyleTweaksConfiguration()),
        )
        val writer = StringWriter()
        template.process(model, writer)
        return writer.toString()
    }
}

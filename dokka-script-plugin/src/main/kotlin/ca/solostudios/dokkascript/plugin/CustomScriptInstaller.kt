/*
 * Copyright (c) 2023 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file CustomScriptInstaller.kt is part of dokka-plugins
 * Last modified on 12-09-2023 10:45 a.m.
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

package ca.solostudios.dokkascript.plugin

import org.jetbrains.dokka.pages.RendererSpecificResourcePage
import org.jetbrains.dokka.pages.RenderingStrategy
import org.jetbrains.dokka.pages.RootPageNode
import org.jetbrains.dokka.plugability.DokkaContext
import org.jetbrains.dokka.plugability.configuration
import org.jetbrains.dokka.transformers.pages.PageTransformer

class CustomScriptInstaller(private val context: DokkaContext) : PageTransformer {
    private val configuration = configuration<DokkaScriptsPlugin, DokkaScriptsConfiguration>(context)

    private val scripts = configuration?.scripts?.map {
        RendererSpecificResourcePage("scripts/${it.name}", emptyList(), RenderingStrategy.Copy(it.absolutePath))
    }.orEmpty()

    private val remoteScripts = configuration?.remoteScripts.orEmpty()

    override fun invoke(input: RootPageNode): RootPageNode {
        val scriptNames = scripts.map { it.name }
        return input.let { root ->
            if (context.configuration.delayTemplateSubstitution)
                root
            else {
                val (resources, other) = input.children.partition { it is RendererSpecificResourcePage }
                root.modified(children = other + resources.filterNot { it.name in scriptNames } + scripts)
            }
        }.transformContentPagesTree { page ->
            val newResources = (page.embeddedResources + scriptNames).toSet() + remoteScripts
            page.modified(embeddedResources = newResources.toList())
        }
    }
}

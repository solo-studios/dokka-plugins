/*
 * Copyright (c) 2023 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file DokkaStyleTweaksConfiguration.kt is part of dokka-plugins
 * Last modified on 15-09-2023 09:39 p.m.
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

import org.jetbrains.dokka.plugability.ConfigurableBlock

data class DokkaStyleTweaksConfiguration(
    /**
     * Enables a cleaner scrollbar.
     *
     * Changes if enabled:
     * - Makes scrollbar gutter transparent
     * - Changes the scrollbar colours
     * - Sets the scrollbar width to auto
     */
    var minimalScrollbar: Boolean = false,
    /**
     * Use a purple highlight when in dark mode.
     *
     * Changes if enabled:
     * - Changes the active section and active tab border colours
     * - Changes the sidemenu active section colour. (slightly darker than other two)
     */
    var darkPurpleHighlight: Boolean = false,
    /**
     * Sets color-scheme to dark when the dark theme is enabled.
     *
     * Changes if enabled:
     * - Sets the `color-scheme` to `dark`
     */
    var darkColorSchemeFix: Boolean = false,
    /**
     * Makes the blockquote look nicer by using a thinner left-border,
     * as well as making it purple for both dark and light modes.
     *
     * Changes if enabled:
     * - Disable default left border
     * - Adds pseudo `:before` element with rounded corners and purple colour (uses active link variable)
     */
    var improvedBlockquoteBorder: Boolean = false,
    /**
     * Makes block quote text slightly lighter than surrounding text.
     * Or, in the case of dark mode, slightly darker.
     *
     * Changes if enabled:
     * - Sets the block quote text colour to a mix of 60% the default font and 40% the background colour.
     */
    var lighterBlockquoteText: Boolean = false,
    /**
     * The font weight applied to the section tabs (eg. the "Members" tab).
     *
     * Changes if not null:
     * - Adds font-weight to the section tabs
     */
    var sectionTabFontWeight: String? = null,
    /**
     * Adds a transition when hovering over the section tabs (eg. the "Members" tab).
     *
     * Changes if enabled:
     * - Adds 0.25 second transition to section tabs.
     */
    var sectionTabTransition: Boolean = false,
    /**
     * Makes the section tab border look nicer, by using rounded corners for the bottom border.
     *
     * Changes if enabled:
     * - Disable default bottom border
     * - Adds pseudo `:before` element with rounded corners and purple colour (uses inactive tab border variable)
     * - Makes border change width to 4px and colour when hovered (uses default font color variable)
     * - Makes border change width to 4px and colour when active (uses active tab border variable)
     */
    var improvedSectionTabBorder: Boolean = false,
    /**
     * Disables wrapping in code blocks.
     *
     * Changes if enabled:
     * - Sets overflow to scroll in code blocks
     * - Sets whitespace to pre in code blocks
     */
    var disableCodeWrapping: Boolean = false,
    // var customHighlightColour: Boolean = false,
) : ConfigurableBlock

<#ftl output_format="CSS">
<#-- @ftlvariable name="styles" type="ca.solostudios.dokkastyles.plugin.DokkaStyleTweaksConfiguration" -->
:root {
    --highlight-color: var(--color-key-blue);
    --highlight-color-hovered: #578af7;
}
<#if styles.minimalScrollbar >
/* Minimal scrollbar */
html {
    scrollbar-width: auto;
}
:root {
    --color-scrollbar-track: transparent;
}
:root.theme-dark {
    --color-scrollbar: hsla(0, 0%, 100%, 0.4);
}
html ::-webkit-scrollbar {
    width: 12px;
    height: 12px;
}

html ::-webkit-scrollbar-thumb {
    border-radius: 8px;
    border: 3px solid transparent;
    background-clip: content-box;
    background-color: #909090;
}

html ::-webkit-scrollbar-thumb:active {
    background-color: #606060;
}
</#if>
<#if styles.darkPurpleHighlight>
/* Dark sidebar purple highlight */
:root {
    --highlight-color: hsla(256, 100%, 66%, 0.75);
    --highlight-color-hovered: hsla(256, 100%, 76%, 0.75);
}

:root.theme-dark {
    --highlight-text-color: hsl(256, 100%, 68%);
    --active-section-color: var(--highlight-text-color);
    --active-tab-border-color: var(--highlight-text-color);
    --sidemenu-section-active-color: var(--highlight-color);
}

.toc--part[data-active] > .toc--row .toc--button,
.toc--part[data-active] > .toc--row .toc--link {
    background-color: var(--highlight-color);
}

.toc--part[data-active] > .toc--row .toc--button:hover,
.toc--part[data-active] > .toc--row .toc--link:hover {
    background-color: var(--highlight-color-hovered)
}
</#if>
<#if styles.darkColorSchemeFix>
:root.theme-dark {
    color-scheme: dark;
}
</#if>
<#if styles.improvedBlockquoteBorder>
/* Improved block quote border */
blockquote {
    border-left: none;
    position: relative;
    padding-left: 1ch;
}
blockquote::before {
    background: var(--hover-link-color);
    border-radius: 8px;
    content: '';
    position: absolute;
    inset: 0 auto 0 0;
    width: 4px;
}
</#if>
<#if styles.lighterBlockquoteText>
/* Lighter block quote text */
blockquote, .theme-dark blockquote {
    color: color-mix(in hsl, var(--default-font-color) 60%, var(--background-color));
}
</#if>
<#if styles.sectionTabFontWeight??>
/* Section tab font weight */
.section-tab, .platform-hinted > .platform-bookmarks-row > .platform-bookmark {
    font-weight: ${styles.sectionTabFontWeight};
}
</#if>
<#if styles.sectionTabTransition>
/* Section tab transitions */
.section-tab, .platform-hinted > .platform-bookmarks-row > .platform-bookmark {
    transition-duration: 0.25s;
    transition-property: color, background-color;
}
</#if>
<#if styles.improvedSectionTabBorder>
/* Improved section tab border */
.section-tab, .section-tab:hover, .section-tab[data-active=""] {
    border-bottom: none;
    position: relative;
}
.section-tab::before {
    background: var(--inactive-tab-border-color);
    border-radius: 8px;
    content: '';
    inset: auto 0 0 0;
    position: absolute;
    height: 1px;
}
.section-tab:hover::before {
    height: 4px;
    background: var(--default-font-color);
    bottom: -2.5px;
}
.section-tab[data-active=""]::before {
    background: var(--active-tab-border-color);
    height: 4px;
    bottom: -2.5px;
}
</#if>
<#if styles.disableCodeWrapping>
/* Disable code block wrapping */
.symbol:not(.token), code {
    overflow: scroll;
    white-space: pre;
}
</#if>
<#if styles.sidebarWidth??>
/* Sidebar width */
:root {
    --sidebar-width: ${styles.sidebarWidth};
}
.sidebar {
    width: min(var(--sidebar-width), 100%);
}
@media (max-width: 759px) {
    .sidebar {
        margin-left: calc(-1 * min(var(--sidebar-width), 100%));
    }

    .sidebar.open ~ #main .menu-toggle {
        margin-left: min(var(--sidebar-width), 100%);
    }
}
</#if>
<#if styles.sidebarFullRowHover>
@media (hover: hover) {
    .toc--button:hover {
        background-color: unset;
    }
}

.toc--button:active {
    background-color: unset;
}

.toc--row:hover > .toc--button {
    background-color: unset;
}

.toc--link:hover {
    background-color: unset;
}

.toc--row:hover {
    background-color: var(--toc-hover-color);
}

.toc--part[data-active] > .toc--row .toc--button:hover,
.toc--part[data-active] > .toc--row .toc--link:hover {
    background-color: unset;
}

.toc--part[data-active] > .toc--row {
    background-color: var(--highlight-color);
}

.toc--part[data-active] > .toc--row:hover {
    background-color: var(--highlight-color-hovered);
}
</#if>
<#if styles.compactSidebar>
.toc--link, .toc--row {
    min-height: unset;
}

.toc--part {
    font-size: 12px;
    line-height: 16px;
}

.toc--part[data-nesting-level="0"] .toc--row > .toc--link:first-child {
    padding-left: calc(40px + var(--size-s2) * 0)
}
.toc--part[data-nesting-level="0"] .toc--button + .toc--link {
    margin-left: calc(40px + var(--size-s2) * 0)
}
.toc--part[data-nesting-level="0"] .toc--button {
    width: calc(40px + var(--size-s2) * 0)
}
.toc--part[data-nesting-level="1"] .toc--row > .toc--link:first-child {
    padding-left: calc(40px + var(--size-s2) * 1)
}
.toc--part[data-nesting-level="1"] .toc--button + .toc--link {
    margin-left: calc(40px + var(--size-s2) * 1)
}
.toc--part[data-nesting-level="1"] .toc--button {
    width: calc(40px + var(--size-s2) * 1);
}
.toc--part[data-nesting-level="2"] .toc--row > .toc--link:first-child {
    padding-left: calc(40px + var(--size-s2) * 2);
}
.toc--part[data-nesting-level="2"] .toc--button + .toc--link {
    margin-left: calc(40px + var(--size-s2) * 2)
}
.toc--part[data-nesting-level="2"] .toc--button {
    width: calc(40px + var(--size-s2) * 2)
}
.toc--part[data-nesting-level="3"] .toc--row > .toc--link:first-child {
    padding-left: calc(40px + var(--size-s2) * 3)
}
.toc--part[data-nesting-level="3"] .toc--button + .toc--link {
    margin-left: calc(40px + var(--size-s2) * 3)
}
.toc--part[data-nesting-level="3"] .toc--button {
    width: calc(40px + var(--size-s2) * 3)
}
.toc--part[data-nesting-level="4"] .toc--row > .toc--link:first-child {
    padding-left: calc(40px + var(--size-s2) * 4)
}
.toc--part[data-nesting-level="4"] .toc--button + .toc--link {
    margin-left: calc(40px + var(--size-s2) * 4)
}
.toc--part[data-nesting-level="4"] .toc--button {
    width: calc(40px + var(--size-s2) * 4)
}
.toc--part[data-nesting-level="5"] .toc--row > .toc--link:first-child {
    padding-left: calc(40px + var(--size-s2) * 5)
}
.toc--part[data-nesting-level="5"] .toc--button + .toc--link {
    margin-left: calc(40px + var(--size-s2) * 5)
}
.toc--part[data-nesting-level="5"] .toc--button {
    width: calc(40px + var(--size-s2) * 5)
}
.toc--part[data-nesting-level="6"] .toc--row > .toc--link:first-child {
    padding-left: calc(40px + var(--size-s2) * 6)
}
.toc--part[data-nesting-level="6"] .toc--button + .toc--link {
    margin-left: calc(40px + var(--size-s2) * 6)
}
.toc--part[data-nesting-level="6"] .toc--button {
    width: calc(40px + var(--size-s2) * 6)
}
.toc--part[data-nesting-level="7"] .toc--row > .toc--link:first-child {
    padding-left: calc(40px + var(--size-s2) * 7)
}
.toc--part[data-nesting-level="7"] .toc--button + .toc--link {
    margin-left: calc(40px + var(--size-s2) * 7)
}
.toc--part[data-nesting-level="7"] .toc--button {
    width: calc(40px + var(--size-s2) * 7)
}
.toc--part[data-nesting-level="8"] .toc--row > .toc--link:first-child {
    padding-left: calc(40px + var(--size-s2) * 8)
}
.toc--part[data-nesting-level="8"] .toc--button + .toc--link {
    margin-left: calc(40px + var(--size-s2) * 8)
}
.toc--part[data-nesting-level="8"] .toc--button {
    width: calc(40px + var(--size-s2) * 8)
}
.toc--part[data-nesting-level="9"] .toc--row > .toc--link:first-child {
    padding-left: calc(40px + var(--size-s2) * 9)
}
.toc--part[data-nesting-level="9"] .toc--button + .toc--link {
    margin-left: calc(40px + var(--size-s2) * 9)
}
.toc--part[data-nesting-level="9"] .toc--button {
    width: calc(40px + var(--size-s2) * 9)
}
.toc--part[data-nesting-level="10"] .toc--row > .toc--link:first-child {
    padding-left: calc(40px + var(--size-s2) * 10)
}
.toc--part[data-nesting-level="10"] .toc--button + .toc--link {
    margin-left: calc(40px + var(--size-s2) * 10)
}
.toc--part[data-nesting-level="10"] .toc--button {
    width: calc(40px + var(--size-s2) * 10)
}
</#if>

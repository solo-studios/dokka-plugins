<#ftl output_format="CSS">
<#-- @ftlvariable name="styles" type="ca.solostudios.dokkastyles.plugin.DokkaStyleTweaksConfiguration" -->
<#if styles.minimalScrollbar >
/* Minimal scrollbar */
html {
    scrollbar-width: auto;
    scrollbar-gutter: stable;
}
:root.theme-dark {
    --color-scrollbar: hsla(0, 0%, 100%, 0.4);
    --color-scrollbar-track: transparent;
}
:root {
    --color-scrollbar-track: transparent;
}
</#if>
<#if styles.darkPurpleHighlight>
/* Dark sidebar purple highlight */
:root.theme-dark {
    --highlight-color: rgba(127, 82, 255, 0.75);
    --highlight-text-color: rgb(135, 90, 255);
    --active-section-color: var(--highlight-text-color);
    --active-tab-border-color: var(--highlight-text-color);
    --sidemenu-section-active-color: var(--highlight-color);
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
    bottom: 0;
    content: '';
    left: 0;
    position: absolute;
    top: 0;
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
    transition-property: all;
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
    bottom: 0;
    right: 0;
    content: '';
    left: 0;
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

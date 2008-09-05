/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.rendering.internal.parser.doxia;

import java.util.Map;

import org.apache.maven.doxia.sink.Sink;
import org.xwiki.rendering.listener.Link;
import org.xwiki.rendering.listener.ListType;
import org.xwiki.rendering.listener.Listener;
import org.xwiki.rendering.listener.SectionLevel;
import org.xwiki.rendering.listener.Format;

public class DoxiaGeneratorListener implements Listener
{
    private Sink sink;

    public DoxiaGeneratorListener(Sink sink)
    {
        this.sink = sink;
    }

    /**
     * {@inheritDoc}
     * 
     * @see Listener#beginFormat(org.xwiki.rendering.listener.Format)
     */
    public void beginFormat(Format format)
    {
        switch (format) {
            case BOLD:
                this.sink.bold();
                break;
            case ITALIC:
                this.sink.italic();
                break;
            case STRIKEDOUT:
                // TODO: Implement when we move to Doxia 1.0 beta 1.
                // See http://jira.codehaus.org/browse/DOXIA-204
                break;
            case UNDERLINED:
                // TODO: Implement when we move to Doxia 1.0 beta 1.
                // See http://jira.codehaus.org/browse/DOXIA-204
                break;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see Listener#endFormat(org.xwiki.rendering.listener.Format)
     */
    public void endFormat(Format format)
    {
        switch (format) {
            case BOLD:
                this.sink.bold_();
                break;
            case ITALIC:
                this.sink.italic_();
                break;
            case STRIKEDOUT:
                // TODO: Implement when we move to Doxia 1.0 beta 1.
                // See http://jira.codehaus.org/browse/DOXIA-204
                break;
            case UNDERLINED:
                // TODO: Implement when we move to Doxia 1.0 beta 1.
                // See http://jira.codehaus.org/browse/DOXIA-204
                break;
        }
    }

    public void beginDocument()
    {
        this.sink.body();
    }

    public void beginList(ListType listType)
    {
        if (listType == ListType.BULLETED) {
            this.sink.list();
        } else {
            // TODO: Handle other numerotations (Roman, etc)
            this.sink.numberedList(Sink.NUMBERING_DECIMAL);
        }
    }

    public void beginListItem()
    {
        this.sink.listItem();
    }

    public void beginMacroMarker(String name, Map<String, String> parameters, String content)
    {
        // Don't do anything since Doxia doesn't have macro markers and anyway we shouldn't
        // do anything.
    }

    public void beginParagraph(Map<String, String> parameters)
    {
        this.sink.paragraph();
    }

    public void beginSection(SectionLevel level)
    {
        if (level == SectionLevel.LEVEL1) {
            this.sink.section1();
        } else if (level == SectionLevel.LEVEL2) {
            this.sink.section2();
        } else if (level == SectionLevel.LEVEL3) {
            this.sink.section3();
        } else if (level == SectionLevel.LEVEL4) {
            this.sink.section4();
        } else if (level == SectionLevel.LEVEL5) {
            this.sink.section5();
        } else if (level == SectionLevel.LEVEL6) {
            // There's no level 6 in Doxia!
            this.sink.section5();
        }
    }

    public void beginXMLElement(String name, Map<String, String> attributes)
    {
        // TODO: Find out what to do...
    }

    public void endDocument()
    {
        this.sink.body_();
    }

    public void endList(ListType listType)
    {
        if (listType == ListType.BULLETED) {
            this.sink.list_();
        } else {
            this.sink.numberedList_();
        }
    }

    public void endListItem()
    {
        this.sink.listItem_();
    }

    public void endMacroMarker(String name, Map<String, String> parameters, String content)
    {
        // Don't do anything since Doxia doesn't have macro markers and anyway we shouldn't
        // do anything.
    }

    public void endParagraph(Map<String, String> parameters)
    {
        this.sink.paragraph_();
    }

    public void endSection(SectionLevel level)
    {
        if (level == SectionLevel.LEVEL1) {
            this.sink.section1_();
        } else if (level == SectionLevel.LEVEL2) {
            this.sink.section2_();
        } else if (level == SectionLevel.LEVEL3) {
            this.sink.section3_();
        } else if (level == SectionLevel.LEVEL4) {
            this.sink.section4_();
        } else if (level == SectionLevel.LEVEL5) {
            this.sink.section5_();
        } else if (level == SectionLevel.LEVEL6) {
            // There's no level 6 in Doxia!
            this.sink.section5_();
        }
    }

    public void endXMLElement(String name, Map<String, String> attributes)
    {
        // TODO: Find out what to do...
    }

    public void onEscape(String escapedString)
    {
        // Note: Doxia doesn't differentiate between escape and verbatim.
        // TODO: Add a jira issue for Doxia to differentiate between both
        onVerbatimInline(escapedString);
    }

    public void onLineBreak()
    {
        this.sink.lineBreak();
    }

    public void onLink(Link link)
    {
        // TODO: Finish the implementation
        this.sink.link(link.getReference());
    }

    public void onInlineMacro(String name, Map<String, String> parameters, String content)
    {
        // Don't do anything since macros have already been transformed so this method
        // should not be called.
    }

    public void onStandaloneMacro(String name, Map<String, String> parameters, String content)
    {
        // Don't do anything since macros have already been transformed so this method
        // should not be called.
    }

    public void onNewLine()
    {
        // Since there's no On NewLine event in Doxia we simply generate text
        this.sink.rawText("\n");
    }

    public void onSpace()
    {
        // Since there's no On Space event in Doxia we simply generate text
        this.sink.rawText(" ");
    }

    public void onSpecialSymbol(String symbol)
    {
        // Since there's no On Special Symbol event in Doxia we simply generate text
        this.sink.rawText(symbol);
    }

    public void onWord(String word)
    {
        this.sink.rawText(word);
    }

    public void onId(String name)
    {
        // TODO: Find out what to do...
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.xwiki.rendering.listener.Listener#onHorizontalLine()
     */
    public void onHorizontalLine()
    {
        this.sink.horizontalRule();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.xwiki.rendering.listener.Listener#onEmptyLines(int)
     */
    public void onEmptyLines(int count)
    {
        // TODO: Find what to do...
    }

    /**
     * {@inheritDoc}
     *
     * @see org.xwiki.rendering.listener.Listener#onVerbatimInline(String)
     */
    public void onVerbatimInline(String protectedString)
    {
        this.sink.verbatim(false);
        this.sink.rawText(protectedString);
        this.sink.verbatim_();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.xwiki.rendering.listener.Listener#onVerbatimStandalone(String)
     */
    public void onVerbatimStandalone(String protectedString)
    {
        onVerbatimInline(protectedString);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.xwiki.rendering.listener.Listener#beginDefinitionList()
     * @since 1.6M2
     */
    public void beginDefinitionList()
    {
        this.sink.definitionList();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.xwiki.rendering.listener.Listener#endDefinitionList()
     * @since 1.6M2
     */
    public void endDefinitionList()
    {
        this.sink.definitionList_();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.xwiki.rendering.listener.Listener#beginDefinitionTerm()
     * @since 1.6M2
     */
    public void beginDefinitionTerm()
    {
        this.sink.definedTerm();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.xwiki.rendering.listener.Listener#beginDefinitionDescription()
     * @since 1.6M2
     */
    public void beginDefinitionDescription()
    {
        this.sink.definition();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.xwiki.rendering.listener.Listener#endDefinitionTerm()
     * @since 1.6M2
     */
    public void endDefinitionTerm()
    {
        this.sink.definedTerm_();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.xwiki.rendering.listener.Listener#endDefinitionDescription() 
     * @since 1.6M2
     */
    public void endDefinitionDescription()
    {
        this.sink.definition_();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.xwiki.rendering.listener.Listener#beginQuotation(java.util.Map)
     * @since 1.6M2
     */
    public void beginQuotation(Map<String, String> parameters)
    {
        // TODO: Doxia doesn't seem to have support for quotation... Find out what to do...
    }

    /**
     * {@inheritDoc}
     *
     * @see org.xwiki.rendering.listener.Listener#endQuotation(java.util.Map)
     * @since 1.6M2
     */
    public void endQuotation(Map<String, String> parameters)
    {
        // TODO: Doxia doesn't seem to have support for quotation... Find out what to do...
    }

    /**
     * {@inheritDoc}
     *
     * @see org.xwiki.rendering.listener.Listener#beginQuotationLine()
     * @since 1.6M2
     */
    public void beginQuotationLine()
    {
        // TODO: Doxia doesn't seem to have support for quotation... Find out what to do...
    }

    /**
     * {@inheritDoc}
     *
     * @see org.xwiki.rendering.listener.Listener#endQuotationLine()  
     * @since 1.6M2
     */
    public void endQuotationLine()
    {
        // TODO: Doxia doesn't seem to have support for quotation... Find out what to do...
    }
}

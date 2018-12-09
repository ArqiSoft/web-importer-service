package sds.webimporter.wikitools.access;

import org.w3c.dom.Element;

/// </summary>
public class SiteInfo {
    /// <summary>
    /// XML node with properties
    /// </summary>

    public Element info;
    /// <summary>
    /// Site name
    /// </summary>
    public String SiteName;
    /// <summary>
    /// Generator
    /// </summary>
    public String Generator;
    /// <summary>
    /// PHP version
    /// </summary>
    public String PHPVersion;
    /// <summary>
    /// DB type
    /// </summary>
    public String DBType;
    /// <summary>
    /// DB version
    /// </summary>
    public String DBVersion;

    /// <summary>
    /// General constructor
    /// </summary>
    /// <param name="elem">XML node that used as initializator for site properties</param>

    public SiteInfo(Element elem) {
        info = elem;

        SiteName = elem.getAttribute("sitename");
        Generator = elem.getAttribute("generator");
        PHPVersion = elem.getAttribute("phpversion");
        DBType = elem.getAttribute("dbtype");
        DBVersion = elem.getAttribute("dbversion");
    }
}

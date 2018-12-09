/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sds.webimporter.wikitools.configs;

public class Infobox {
    //[ConfigurationProperty("type", IsKey = true, IsRequired = true)]

    public String Type;

    //[ConfigurationProperty("inherits", IsKey = true)]
    public String Inherits;

    //[ConfigurationProperty("", IsRequired = true, IsDefaultCollection = true)]
    public InfoboxElementCollection Elements;

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sds.webimporter.wikitools.configs;

public class InfoboxElement {
    //[ConfigurationProperty("id", IsKey = true, IsRequired = true)]
    public String Id;

    //[ConfigurationProperty("name", IsKey = true, IsRequired = true)]
    public String Name;

    //[ConfigurationProperty("fields", IsKey = true, IsRequired = true)]
    public String Fields;


    //[ConfigurationProperty("split", IsKey = true)]
    public boolean Split;

}
